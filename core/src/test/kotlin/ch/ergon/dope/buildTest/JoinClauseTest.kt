package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.bucket.useIndex
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.min
import ch.ergon.dope.resolvable.expression.rowscope.alias
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class JoinClauseTest {
    private val route = someBucket("route")
    private val airline = someBucket("airline")

    @Test
    fun `should support join`() {
        val expected =
            "SELECT * FROM `route` JOIN `airline` ON `route`.`airlineid` = META(`airline`).`id` WHERE `airline`.`country` = \"France\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                route,
            ).join(
                airline,
                condition = someStringField("airlineid", route).isEqualTo(
                    meta(airline).id,
                ),
            ).where(
                someStringField("country", airline).isEqualTo(
                    "France".toDopeType(),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support left join`() {
        val expected =
            "SELECT * FROM `route` USE INDEX () LEFT JOIN `airline` ON `route`.`airlineid` = META(`airline`).`id` " +
                "WHERE `route`.`sourceairport` = \"SFO\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                route.useIndex(),
            ).leftJoin(
                airline,
                condition = someStringField("airlineid", route).isEqualTo(
                    meta(airline).id,
                ),
            ).where(
                someStringField("sourceairport", route).isEqualTo("SFO".toDopeType()),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support right outer join`() {
        val expected =
            "SELECT * FROM `route` RIGHT JOIN `airline` ON `route`.`airlineid` = META(`airline`).`id` WHERE `route`.`sourceairport` = \"SFO\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                route,
            ).rightJoin(
                airline,
                condition = someStringField("airlineid", route).isEqualTo(
                    meta(
                        airline,
                    ).id,
                ),
            ).where(
                someStringField("sourceairport", route).isEqualTo(
                    "SFO".toDopeType(),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support complex inner join`() {
        val expected = "SELECT `route`.`airlineid`, `airline`.`iota`, `route`.`sourceairport`, " +
            "`route`.`destinationairport` FROM `route` INNER JOIN `airline` ON `route`.`airlineid` = " +
            "META(`airline`).`id` WHERE `route`.`destinationairport` = \"SFO\" ORDER BY `sourceairport`"

        val actual = QueryBuilder.select(
            someStringField("airlineid", route),
            someStringField("iota", airline),
            someStringField("sourceairport", route),
            someStringField("destinationairport", route),
        ).from(
            route,
        ).innerJoin(
            airline,
            condition = someStringField("airlineid", route).isEqualTo(
                meta(
                    airline,
                ).id,
            ),
        ).where(
            someStringField("destinationairport", route).isEqualTo(
                "SFO".toDopeType(),
            ),
        ).orderBy(
            someStringField("sourceairport"),
        ).build().queryString

        assertEquals(
            unifyString(expected),
            actual,
        )
    }

    @Test
    fun `Left Outer Join of US airports in the same city as a landmark`() {
        val aport = someBucket("airport").alias("aport")
        val lmark = someBucket("landmark").alias("lmark")

        val expected =
            "SELECT DISTINCT MIN(`aport`.`airportname`) AS `Airport__Name`, " +
                "MIN(`aport`.`tz`) AS `Airport__Time`, MIN(`lmark`.`name`) AS `Landmark_Name` " +
                "FROM `airport` AS `aport` LEFT JOIN `landmark` AS `lmark` ON (`aport`.`city` = " +
                "`lmark`.`city` AND `lmark`.`country` = \"United States\") GROUP BY " +
                "`aport`.`airportname` ORDER BY `aport`.`airportname` LIMIT 4"

        val actual = QueryBuilder.selectDistinct(
            min(
                someStringField("airportname", aport),
            ).alias(
                "Airport__Name",
            ),
            min(
                someStringField("tz", aport),
            ).alias(
                "Airport__Time",
            ),
            min(
                someStringField("name", lmark),
            ).alias(
                "Landmark_Name",
            ),
        ).from(
            aport,
        ).leftJoin(
            lmark,
            condition = someStringField("city", aport).isEqualTo(
                someStringField("city", lmark),
            ).and(
                someStringField("country", lmark).isEqualTo(
                    "United States".toDopeType(),
                ),
            ),
        ).groupBy(
            someStringField("airportname", aport),
        ).orderBy(
            someStringField("airportname", aport),
        ).limit(
            4,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Right Outer Join of US airports in the same city as a landmark`() {
        val aport = someBucket("airport").alias("aport")
        val lmark = someBucket("landmark").alias("lmark")

        val expected =
            "SELECT DISTINCT MIN(`aport`.`airportname`) AS `Airport__Name`, " +
                "MIN(`aport`.`tz`) AS `Airport__Time`, MIN(`lmark`.`name`) AS `Landmark_Name` " +
                "FROM `airport` AS `aport` RIGHT JOIN `landmark` AS `lmark` ON (`aport`.`city` = " +
                "`lmark`.`city` AND `lmark`.`country` = \"United States\") GROUP BY " +
                "`aport`.`airportname` ORDER BY `aport`.`airportname` LIMIT 4"

        val actual = QueryBuilder.selectDistinct(
            min(
                someStringField("airportname", aport),
            ).alias(
                "Airport__Name",
            ),
            min(
                someStringField("tz", aport),
            ).alias(
                "Airport__Time",
            ),
            min(
                someStringField("name", lmark),
            ).alias(
                "Landmark_Name",
            ),
        ).from(
            aport,
        ).rightJoin(
            lmark,
            condition = someStringField("city", aport).isEqualTo(
                someStringField("city", lmark),
            ).and(
                someStringField("country", lmark).isEqualTo(
                    "United States".toDopeType(),
                ),
            ),
        ).groupBy(
            someStringField("airportname", aport),
        ).orderBy(
            someStringField("airportname", aport),
        ).limit(
            4.toDopeType(),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Right Outer Join`() {
        val airport = someBucket("airport")
        val route = someBucket("route")
        val expected = "SELECT DISTINCT `subquery`.`destinationairport` " +
            "FROM `airport` " +
            "JOIN (SELECT `destinationairport`, `sourceairport` FROM `route`) AS `subquery` " +
            "ON `airport`.`faa` = `subquery`.`sourceairport` " +
            "WHERE `airport`.`city` = \"San Francisco\""

        val actual = QueryBuilder
            .selectDistinct(someStringField("destinationairport", someBucket("subquery")))
            .from(airport)
            .join(
                QueryBuilder
                    .select(someStringField("destinationairport"), someStringField("sourceairport"))
                    .from(route)
                    .alias("subquery"),
                condition = someStringField("faa", airport).isEqualTo(someStringField("sourceairport", someBucket("subquery"))),
            )
            .where(someStringField("city", airport).isEqualTo("San Francisco"))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Inner Lookup Join`() {
        val expected = "SELECT DISTINCT `route`.`destinationairport`, `route`.`stops`, " +
            "`route`.`airline`, `airline`.`name`, `airline`.`callsign` " +
            "FROM `route` JOIN `airline` ON KEYS `route`.`airlineid` " +
            "WHERE (`route`.`sourceairport` = \"SFO\" AND `route`.`stops` = 0) LIMIT 4"

        val actual = QueryBuilder.selectDistinct(
            someStringField("destinationairport", route),
            someStringField("stops", route),
            someStringField("airline", route),
            someStringField("name", airline),
            someStringField("callsign", airline),
        ).from(
            route,
        ).join(
            airline,
            key = someStringField("airlineid", route),
        ).where(
            someStringField("sourceairport", route).isEqualTo(
                "SFO".toDopeType(),
            ).and(
                someNumberField("stops", route).isEqualTo(
                    0.toDopeType(),
                ),
            ),
        ).limit(
            4,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Left Outer Lookup Join`() {
        val expected = "SELECT `route`.`airline`, `route`.`sourceairport`, " +
            "`route`.`destinationairport`, `airline`.`callsign` FROM `route` LEFT JOIN `airline` " +
            "ON KEYS `route`.`airlineid` WHERE (`route`.`destinationairport` = " +
            "\"ATL\" AND `route`.`sourceairport` = \"SEA\")"

        val actual = QueryBuilder.select(
            someStringField("airline", route),
            someStringField("sourceairport", route),
            someStringField("destinationairport", route),
            someStringField("callsign", airline),
        ).from(
            route,
        ).leftJoin(
            airline,
            key = someStringField("airlineid", route),
        ).where(
            someStringField("destinationairport", route).isEqualTo(
                "ATL".toDopeType(),
            ).and(
                someStringField("sourceairport", route).isEqualTo(
                    "SEA".toDopeType(),
                ),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX join to flip the direction`() {
        val expected = "SELECT DISTINCT `route`.`destinationairport`, " +
            "`route`.`stops`, `route`.`airline`, `airline`.`name`, `airline`.`callsign` " +
            "FROM `route` JOIN `airline` ON KEYS `route`.`airlineid` " +
            "WHERE `airline`.`icao` = \"SWA\" LIMIT 4"

        val actual = QueryBuilder.selectDistinct(
            someStringField("destinationairport", route),
            someStringField("stops", route),
            someStringField("airline", route),
            someStringField("name", airline),
            someStringField("callsign", airline),
        ).from(
            route,
        ).join(
            airline,
            key = someStringField("airlineid", route),
        ).where(
            someStringField("icao", airline).isEqualTo("SWA".toDopeType()),
        ).limit(
            4,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX join to flip the direction with on key for`() {
        val expected = "SELECT * FROM `airline` JOIN `route` ON KEY `route`.`airlineid` FOR `airline`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                airline,
            ).join(
                route,
                key = someStringField("airlineid", route),
                bucket = airline,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Use INDEX inner join to flip the direction with on key for`() {
        val expected = "SELECT * FROM `airline` INNER JOIN `route` ON KEY `route`.`airlineid` FOR `airline`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                airline,
            ).innerJoin(
                route,
                key = someStringField("airlineid", route),
                bucket = airline,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Use INDEX left join to flip the direction with on key for`() {
        val expected = "SELECT * FROM `airline` LEFT JOIN `route` ON KEY `route`.`airlineid` FOR `airline`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                airline,
            ).leftJoin(
                route,
                key = someStringField("airlineid", route),
                bucket = airline,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Simple Join Example`() {
        val expected = "SELECT * " +
            "FROM `route` AS `r` " +
            "JOIN `airline` AS `a` " +
            "ON `r`.`airlineid` = META(`a`).`id`"

        val r = someBucket("route").alias("r")
        val a = airline.alias("a")

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                r,
            ).join(
                a,
                condition = someStringField("airlineid", r).isEqualTo(
                    meta(a).id,
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Simple Join Example 2`() {
        val expected = "SELECT * FROM `route` AS `r` JOIN `airline` ON KEYS `r`.`airlineid`"

        val r = someBucket("route").alias("r")

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                r,
            ).join(
                airline,
                key = someStringField("airlineid", r),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX inner join to flip the direction`() {
        val expected = "SELECT DISTINCT `route`.`destinationairport`, " +
            "`route`.`stops`, `route`.`airline`, `airline`.`name`, `airline`.`callsign` " +
            "FROM `route` INNER JOIN `airline` ON KEYS `route`.`airlineid` " +
            "WHERE `airline`.`icao` = \"SWA\" LIMIT 4"

        val actual = QueryBuilder.selectDistinct(
            someStringField("destinationairport", route),
            someStringField("stops", route),
            someStringField("airline", route),
            someStringField("name", airline),
            someStringField("callsign", airline),
        ).from(
            route,
        ).innerJoin(
            airline,
            key = someStringField("airlineid", route),
        ).where(
            someStringField("icao", airline).isEqualTo("SWA".toDopeType()),
        ).limit(
            4,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Very minimal Example`() {
        val expected = "SELECT *\n" +
            "FROM `airline` AS `a1`\n" +
            "JOIN `airline` AS `a2` ON `a1`.`id` = `a2`.`id`\n"
        val a1 = airline.alias("a1")
        val a2 = airline.alias("a2")

        val actual = QueryBuilder
            .selectFrom(
                a1,
            ).join(
                a2,
                condition = someStringField("id", a1).isEqualTo(someStringField("id", a2)),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support multiple joins`() {
        val expected = "SELECT *\n" +
            "FROM `airline` AS `al`\n" +
            "JOIN `airport` AS `ap` ON `al`.`id` = `ap`.`id`\n" +
            "JOIN `city` AS `c` ON `ap`.`id` = `c`.`id`\n"
        val airline = airline.alias("al")
        val airport = someBucket("airport").alias("ap")
        val city = someBucket("city").alias("c")

        val actual = QueryBuilder
            .selectFrom(
                airline,
            ).join(
                airport,
                condition = someStringField("id", airline).isEqualTo(someStringField("id", airport)),
            ).join(
                city,
                condition = someStringField("id", airport).isEqualTo(someStringField("id", city)),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }
}
