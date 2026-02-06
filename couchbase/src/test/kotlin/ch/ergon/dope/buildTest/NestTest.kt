package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someObjectArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NestTest {
    val route = someKeyspace("route")
    val airport = someKeyspace("airport")

    @BeforeTest
    fun setup() {
    }

    @Test
    fun `should support inner nest on condition`() {
        val expected = "SELECT * FROM `airport` " +
            "INNER NEST `route` ON `airport`.`faa` = `route`.`sourceairport` " +
            "WHERE `airport`.`city` = \"Toulouse\" ORDER BY `airport`.`airportname`"

        val actual: String = QueryBuilder
            .selectFrom(
                airport,
            ).innerNest(
                route,
                condition = someStringField("faa", airport).isEqualTo(
                    someStringField("sourceairport", route),
                ),
            ).where(
                someStringField("city", airport).isEqualTo("Toulouse"),
            ).orderBy(
                someStringField("airportname", airport),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nest with subquery`() {
        val r1 = route.alias("r1")
        val expected = "SELECT * FROM `airport` " +
            "NEST (SELECT `r1`.* FROM `route` AS `r1` WHERE `airport`.`faa` = `r1`.`sourceairport`) AS `r` ON TRUE " +
            "WHERE `airport`.`city` = \"Toulouse\" ORDER BY `airport`.`airportname`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                airport,
            ).nest(
                QueryBuilder
                    .select(r1.asterisk()).from(r1).where(
                        someStringField("faa", airport).isEqualTo(
                            someStringField("sourceairport", r1),
                        ),
                    ).alias("r"),
                condition = TRUE,
            ).where(
                someStringField("city", airport).isEqualTo("Toulouse"),
            ).orderBy(
                someStringField("airportname", airport),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest on condition`() {
        val airportBucket = airport.alias("a")
        val routeBucket = route.alias("r")
        val expected = "SELECT * FROM `airport` AS `a` " +
            "LEFT NEST `route` AS `r` ON `a`.`faa` = `r`.`sourceairport` " +
            "WHERE `a`.`city` = \"Toulouse\" ORDER BY `a`.`airportname`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(airportBucket)
            .leftNest(
                routeBucket,
                condition = someStringField("faa", airportBucket).isEqualTo(someStringField("sourceairport", routeBucket)),
            ).where(
                someStringField("city", airportBucket).isEqualTo("Toulouse"),
            ).orderBy(
                someStringField("airportname", airportBucket),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest on keys`() {
        val expected = "SELECT * FROM `route` INNER NEST `airport` ON KEYS `route`.`airportid` LIMIT 1"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                route,
            )
            .innerNest(
                airport,
                key = someStringField("airportid", route),
            ).limit(1).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest on key for keyspace`() {
        val airportBucket = airport.alias("a")
        val routeBucket = route.alias("r")
        val expected = "SELECT * FROM `airport` AS `a` " +
            "INNER NEST `route` AS `r` ON KEY `r`.`airportid` FOR `a` LIMIT 1"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(airportBucket)
            .innerNest(
                routeBucket,
                key = someStringField("airportid", routeBucket),
                keyspace = airportBucket,
            ).limit(1).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple from terms`() {
        val routeBucket = route.alias("r")
        val airportBucket = airport.alias("a")
        val landmarkBucket = someKeyspace("landmark").alias("l")
        val expected = "SELECT * FROM `route` AS `r` " +
            "JOIN `airport` AS `a` ON `r`.`destinationairport` = `a`.`faa` " +
            "NEST `landmark` AS `l` ON `a`.`city` = `l`.`city` " +
            "UNNEST `r`.`schedule` AS `s` " +
            "WHERE `s`.`day` = 1 " +
            "LIMIT 10"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                routeBucket,
            ).join(
                airportBucket,
                condition = someStringField("destinationairport", routeBucket).isEqualTo(
                    someStringField("faa", airportBucket),
                ),
            ).nest(
                landmarkBucket,
                condition = someStringField("city", airportBucket).isEqualTo(
                    someStringField("city", landmarkBucket),
                ),
            ).unnest(
                someObjectArrayField("schedule", routeBucket).alias("s"),
            ).where(
                someNumberField("day", someKeyspace("s")).isEqualTo(1), // this is so hack-y
            ).limit(10).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
