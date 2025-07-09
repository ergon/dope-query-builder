package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
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
    private val route = someBucket("route")
    private val airport = someBucket("airport")

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
            ).build().queryString

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
                QueryBuilder.select(r1.asterisk()).from(r1).where(
                    someStringField("faa", airport).isEqualTo(
                        someStringField("sourceairport", r1),
                    ),
                ).alias("r"),
                condition = TRUE,
            ).where(
                someStringField("city", airport).isEqualTo("Toulouse"),
            ).orderBy(
                someStringField("airportname", airport),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest on condition`() {
        val a = airport.alias("a")
        val r = route.alias("r")
        val expected = "SELECT * FROM `airport` AS `a` " +
            "LEFT NEST `route` AS `r` ON `a`.`faa` = `r`.`sourceairport` " +
            "WHERE `a`.`city` = \"Toulouse\" ORDER BY `a`.`airportname`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(a)
            .leftNest(
                r,
                condition = someStringField("faa", a).isEqualTo(someStringField("sourceairport", r)),
            ).where(
                someStringField("city", a).isEqualTo("Toulouse"),
            ).orderBy(
                someStringField("airportname", a),
            ).build().queryString

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
            ).limit(1).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest on key for bucket`() {
        val a = airport.alias("a")
        val r = route.alias("r")
        val expected = "SELECT * FROM `airport` AS `a` " +
            "INNER NEST `route` AS `r` ON KEY `r`.`airportid` FOR `a` LIMIT 1"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(a)
            .innerNest(
                r,
                key = someStringField("airportid", r),
                bucket = a,
            ).limit(1).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple from terms`() {
        val r = route.alias("r")
        val a = airport.alias("a")
        val l = someBucket("landmark").alias("l")
        val expected = "SELECT * FROM `route` AS `r` " +
            "JOIN `airport` AS `a` ON `r`.`destinationairport` = `a`.`faa` " +
            "NEST `landmark` AS `l` ON `a`.`city` = `l`.`city` " +
            "UNNEST `r`.`schedule` AS `s` " +
            "WHERE `s`.`day` = 1 " +
            "LIMIT 10"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                r,
            ).join(
                a,
                condition = someStringField("destinationairport", r).isEqualTo(
                    someStringField("faa", a),
                ),
            ).nest(
                l,
                condition = someStringField("city", a).isEqualTo(
                    someStringField("city", l),
                ),
            ).unnest(
                someObjectArrayField("schedule", r).alias("s"),
            ).where(
                someNumberField("day", someBucket("s")).isEqualTo(1), // this is so hack-y
            ).limit(10).build().queryString

        assertEquals(expected, actual)
    }
}
