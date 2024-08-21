package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.coalesce
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.decode
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.ifMissing
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.ifMissingOrNull
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.ifNull
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.nvl
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.nvl2
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalUnknownFunctionsTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support coalesce in query`() {
        val expected = "SELECT COALESCE(`stringField`, CONCAT(\"some\", \"string\"), \"someString\")"

        val actual = create
            .select(
                coalesce(
                    someStringField(),
                    concat("some", "string"),
                    someString().toDopeType(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode in query`() {
        val bucket = someBucket("airport").alias("a")
        val expected = "SELECT `a`.`airportname` AS `Airport`, " +
            "DECODE(`a`.`tz`, \"Pacific/Honolulu\", -10, " +
            "\"America/Anchorage\", -9, " +
            "\"America/Los_Angeles\", -8, " +
            "\"America/Denver\", -7, " +
            "\"America/Chicago\", -6, " +
            "\"America/New_York\", -5, 0) AS `UTCOffset` " +
            "FROM `airport` AS `a` " +
            "WHERE (`a`.`country` = \"United States\" AND `a`.`geo.alt` > 1000) " +
            "LIMIT 5"

        val actual = create
            .select(
                someStringField("airportname", bucket).alias("Airport"),
                decode(
                    someStringField("tz", bucket),
                    "Pacific/Honolulu".resultsIn(-10),
                    "America/Anchorage".resultsIn(-9),
                    "America/Los_Angeles".resultsIn(-8),
                    "America/Denver".resultsIn(-7),
                    "America/Chicago".resultsIn(-6),
                    "America/New_York".resultsIn(-5),
                    default = 0.toDopeType(),
                ).alias("UTCOffset"),
            ).from(
                bucket,
            ).where(
                someStringField("country", bucket).isEqualTo("United States").and(
                    someNumberField("geo.alt", bucket).isGreaterThan(1000),
                ),
            ).limit(
                5,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing in query`() {
        val expected = "SELECT IFMISSING(`stringField`, CONCAT(\"some\", \"string\"), \"someString\")"

        val actual = create
            .select(
                ifMissing(
                    someStringField(),
                    concat("some", "string"),
                    someString().toDopeType(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null in query`() {
        val expected = "SELECT IFMISSINGORNULL(`stringField`, CONCAT(\"some\", \"string\"), \"someString\")"

        val actual = create
            .select(
                ifMissingOrNull(
                    someStringField(),
                    concat("some", "string"),
                    someString().toDopeType(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null in query`() {
        val expected = "SELECT IFNULL(`stringField`, CONCAT(\"some\", \"string\"), \"someString\")"

        val actual = create
            .select(
                ifNull(
                    someStringField(),
                    concat("some", "string"),
                    someString().toDopeType(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl in query`() {
        val expected = "SELECT `name` AS `Name`, NVL(`iata`, \"n/a\") AS `IATA` " +
            "FROM `airline` " +
            "LIMIT 5"

        val actual = create
            .select(
                someStringField("name").alias("Name"),
                nvl(
                    someStringField("iata"),
                    "n/a",
                ).alias("IATA"),
            ).from(
                someBucket("airline"),
            ).limit(
                5,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nvl2 in query`() {
        val expected = "SELECT `name` AS `Name`, NVL2(`directions`, \"Yes\", \"No\") AS `DirectionsAvailable` " +
            "FROM `hotel` " +
            "LIMIT 5"

        val actual = create
            .select(
                someStringField("name").alias("Name"),
                nvl2(
                    someStringArrayField("directions"),
                    "Yes",
                    "No",
                ).alias("DirectionsAvailable"),
            ).from(
                someBucket("hotel"),
            ).limit(
                5,
            ).build().queryString

        assertEquals(expected, actual)
    }
}
