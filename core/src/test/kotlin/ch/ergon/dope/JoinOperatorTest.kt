package ch.ergon.dope

import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.fromable.field
import ch.ergon.dope.resolvable.fromable.innerJoin
import ch.ergon.dope.resolvable.fromable.join
import ch.ergon.dope.resolvable.fromable.leftJoin
import ch.ergon.dope.resolvable.fromable.rightJoin
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class JoinOperatorTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support join`() {
        val expected = "SELECT * FROM route JOIN airline ON route.airlineid = META(airline).id WHERE airline.country = \"France\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Route.join(
                    TestBucket.Airline,
                    onCondition = TestBucket.Route.airlineid.isEqualTo(
                        meta(TestBucket.Airline).id,
                    ),
                ),
            ).where(
                TestBucket.Airline.country.isEqualTo(
                    "France".toStringType(),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support left join`() {
        val expected = "SELECT * FROM route LEFT JOIN airline ON route.airlineid = META(airline).id WHERE route.sourceairport = \"SFO\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Route.leftJoin(
                    TestBucket.Airline,
                    onCondition = TestBucket.Route.airlineid.isEqualTo(
                        meta(TestBucket.Airline).id,
                    ),
                ),
            ).where(
                TestBucket.Route.sourceairport.isEqualTo("SFO".toStringType()),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support right outer join`() {
        val expected = "SELECT * FROM route RIGHT JOIN airline ON route.airlineid = META(airline).id WHERE route.sourceairport = \"SFO\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Route.rightJoin(
                    TestBucket.Airline,
                    onCondition = TestBucket.Route.airlineid.isEqualTo(
                        meta(
                            TestBucket.Airline,
                        ).id,
                    ),
                ),
            ).where(
                TestBucket.Route.sourceairport.isEqualTo(
                    "SFO".toStringType(),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Should support complex inner join`() {
        val expected = "SELECT route.airlineid, airline.iota, route.sourceairport, " +
            "route.destinationairport FROM route INNER JOIN airline ON route.airlineid = " +
            "META(airline).id WHERE route.destinationairport = \"SFO\" ORDER BY route.sourceairport"

        val actual = create.select(
            TestBucket.Route.airlineid,
            TestBucket.Airline.iota,
            TestBucket.Route.sourceairport,
            TestBucket.Route.destinationairport,
        ).from(
            TestBucket.Route.innerJoin(
                TestBucket.Airline,
                onCondition = TestBucket.Route.airlineid.isEqualTo(
                    meta(
                        TestBucket.Airline,
                    ).id,
                ),
            ),
        ).where(
            TestBucket.Route.destinationairport.isEqualTo(
                "SFO".toStringType(),
            ),
        ).orderBy(
            TestBucket.Route.sourceairport,
        ).build()

        assertEquals(
            unifyString(expected),
            actual,
        )
    }

    @Test
    fun `Left Outer Join of US airports in the same city as a landmark`() {
        val aport = TestBucket.Airport.alias("aport")
        val lmark = TestBucket.Landmark.alias("lmark")

        val expected =
            "SELECT DISTINCT MIN(aport.airportname) AS Airport__Name, " +
                "MIN(aport.tz) AS Airport__Time, MIN(lmark.name) AS Landmark_Name " +
                "FROM airport AS aport LEFT JOIN landmark AS lmark ON aport.city = " +
                "lmark.city AND lmark.country = \"United States\" GROUP BY " +
                "aport.airportname ORDER BY aport.airportname LIMIT 4"

        val actual = create.selectDistinct(
            min(
                aport.field("airportname"),
            ).alias(
                "Airport__Name",
            ),
            min(
                aport.field("tz"),
            ).alias(
                "Airport__Time",
            ),
            min(
                lmark.field("name"),
            ).alias(
                "Landmark_Name",
            ),
        ).from(
            aport.leftJoin(
                lmark,
                onCondition = aport.field("city").isEqualTo(
                    lmark.field("city"),
                ).and(
                    lmark.field("country").isEqualTo(
                        "United States".toStringType(),
                    ),
                ),
            ),
        ).groupBy(
            aport.field(
                "airportname",
            ),
        )
            .orderBy(
                aport.field(
                    "airportname",
                ),
            )
            .limit(
                4,
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Right Outer Join of US airports in the same city as a landmark`() {
        val aport = TestBucket.Airport.alias("aport")
        val lmark = TestBucket.Landmark.alias("lmark")

        val expected =
            "SELECT DISTINCT MIN(aport.airportname) AS Airport__Name, " +
                "MIN(aport.tz) AS Airport__Time, MIN(lmark.name) AS Landmark_Name " +
                "FROM airport AS aport RIGHT JOIN landmark AS lmark ON aport.city = " +
                "lmark.city AND lmark.country = \"United States\" GROUP BY " +
                "aport.airportname ORDER BY aport.airportname LIMIT 4"

        val actual = create.selectDistinct(
            min(
                aport.field(
                    "airportname",
                ),
            ).alias(
                "Airport__Name",
            ),
            min(
                aport.field(
                    "tz",
                ),
            ).alias(
                "Airport__Time",
            ),
            min(
                lmark.field(
                    "name",
                ),
            ).alias(
                "Landmark_Name",
            ),
        ).from(
            aport.rightJoin(
                lmark,
                onCondition = aport.field(
                    "city",
                ).isEqualTo(
                    lmark.field(
                        "city",
                    ),
                ).and(
                    lmark.field(
                        "country",
                    ).isEqualTo(
                        "United States".toStringType(),
                    ),
                ),
            ),
        ).groupBy(
            aport.field(
                "airportname",
            ),
        ).orderBy(
            aport.field(
                "airportname",
            ),
        ).limit(
            4.toNumberType(),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

//    @Test TODO: DOPE-170
//    fun `Right Outer Join `() {
//        val expected = "SELECT DISTINCT subquery.destinationairport " +
//            "FROM airport JOIN (SELECT destinationairport, sourceairport " +
//            "FROM route) AS subquery ON airport.faa = subquery.sourceairport WHERE airport.city = \"San Francisco\""
//
//        val subquery = create.select(Collections.Route.destinationairport, Collections.Route.sourceairport).from(Collections.Route).asSubquery(
//            "subquery",
//        )
//        subquery.printIt()
//        assertEquals(true, true)
//    }

    @Test
    fun `Inner Lookup Join`() {
        val expected = "SELECT DISTINCT route.destinationairport, route.stops, " +
            "route.airline, airline.name, airline.callsign " +
            "FROM route JOIN airline ON KEYS route.airlineid " +
            "WHERE route.sourceairport = \"SFO\" AND route.stops = 0 LIMIT 4"

        val actual = create.selectDistinct(
            TestBucket.Route.destinationairport,
            TestBucket.Route.stops,
            TestBucket.Route.airline,
            TestBucket.Airline.aname,
            TestBucket.Airline.callsign,
        ).from(
            TestBucket.Route.join(
                TestBucket.Airline,
                onKeys = TestBucket.Route.airlineid,
            ),
        ).where(
            TestBucket.Route.sourceairport.isEqualTo(
                "SFO".toStringType(),
            ).and(
                TestBucket.Route.stops.isEqualTo(
                    0.toNumberType(),
                ),
            ),
        ).limit(
            4,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Left Outer Lookup Join`() {
        val expected = "SELECT route.airline, route.sourceairport, " +
            "route.destinationairport, airline.callsign FROM route LEFT JOIN airline " +
            "ON KEYS route.airlineid WHERE route.destinationairport = " +
            "\"ATL\" AND route.sourceairport = \"SEA\""

        val actual = create.select(
            TestBucket.Route.airline,
            TestBucket.Route.sourceairport,
            TestBucket.Route.destinationairport,
            TestBucket.Airline.callsign,
        ).from(
            TestBucket.Route.leftJoin(
                TestBucket.Airline,
                onKeys = TestBucket.Route.airlineid,
            ),
        ).where(
            TestBucket.Route.destinationairport.isEqualTo(
                "ATL".toStringType(),
            ).and(
                TestBucket.Route.sourceairport.isEqualTo(
                    "SEA".toStringType(),
                ),
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX join to flip the direction`() {
        val expected = "SELECT DISTINCT route.destinationairport, " +
            "route.stops, route.airline, airline.name, airline.callsign " +
            "FROM route JOIN airline ON KEYS route.airlineid " +
            "WHERE airline.icao = \"SWA\" LIMIT 4"

        val actual = create.selectDistinct(
            TestBucket.Route.destinationairport,
            TestBucket.Route.stops,
            TestBucket.Route.airline,
            TestBucket.Airline.aname,
            TestBucket.Airline.callsign,
        ).from(
            TestBucket.Route.join(
                TestBucket.Airline,
                onKeys = TestBucket.Route.airlineid,
            ),
        ).where(
            TestBucket.Airline.icao.isEqualTo("SWA".toStringType()),
        ).limit(
            4,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX right join to flip the direction`() {
        val expected = "SELECT DISTINCT route.destinationairport, " +
            "route.stops, route.airline, airline.name, airline.callsign " +
            "FROM route RIGHT JOIN airline ON KEYS route.airlineid " +
            "WHERE airline.icao = \"SWA\" LIMIT 4"

        val actual = create.selectDistinct(
            TestBucket.Route.destinationairport,
            TestBucket.Route.stops,
            TestBucket.Route.airline,
            TestBucket.Airline.aname,
            TestBucket.Airline.callsign,
        ).from(
            TestBucket.Route.rightJoin(
                TestBucket.Airline,
                onKeys = TestBucket.Route.airlineid,
            ),
        ).where(
            TestBucket.Airline.icao.isEqualTo("SWA".toStringType()),
        ).limit(
            4,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Simple Join Example`() {
        val expected = "SELECT * " +
            "FROM route AS r " +
            "JOIN airline AS a " +
            "ON r.airlineid = META(a).id"

        val r = TestBucket.Route.alias("r")
        val a = TestBucket.Airline.alias("a")

        val actual = create
            .selectAll()
            .from(
                r.join(
                    a,
                    onCondition = r.field(
                        "airlineid",
                    ).isEqualTo(
                        meta(
                            a,
                        ).id,
                    ),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Simple Join Example 2`() {
        val expected = "SELECT * FROM route AS r JOIN airline ON KEYS r.airlineid"

        val r = TestBucket.Route.alias("r")

        val actual = create
            .selectAll()
            .from(
                r.join(
                    TestBucket.Airline,
                    onKeys = r.field(
                        "airlineid",
                    ),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Use INDEX inner join to flip the direction`() {
        val expected = "SELECT DISTINCT route.destinationairport, " +
            "route.stops, route.airline, airline.name, airline.callsign " +
            "FROM route INNER JOIN airline ON KEYS route.airlineid " +
            "WHERE airline.icao = \"SWA\" LIMIT 4"

        val actual = create.selectDistinct(
            TestBucket.Route.destinationairport,
            TestBucket.Route.stops,
            TestBucket.Route.airline,
            TestBucket.Airline.aname,
            TestBucket.Airline.callsign,
        ).from(
            TestBucket.Route.innerJoin(
                TestBucket.Airline,
                onKeys = TestBucket.Route.airlineid,
            ),
        ).where(
            TestBucket.Airline.icao.isEqualTo("SWA".toStringType()),
        ).limit(
            4,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `Very minimal Example`() {
        val expected = "SELECT *\n" +
            "FROM airline AS a1\n" +
            "JOIN airline AS a2 ON a1.id = a2.id\n"
        val a1 = TestBucket.Airline.alias("a1")
        val a2 = TestBucket.Airline.alias("a2")

        val actual = create
            .selectFrom(
                a1.join(
                    a2,
                    onCondition = a1.field("id").isEqualTo(a2.field("id")),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }
}
