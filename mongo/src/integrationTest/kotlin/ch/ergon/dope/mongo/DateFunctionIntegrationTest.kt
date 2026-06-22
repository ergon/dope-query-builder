package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType.DAY_OF_WEEK
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType.DAY_OF_YEAR
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType.ISO_WEEK
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.YEAR
import ch.ergon.dope.resolvable.expression.type.function.date.addDateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.clockMillis
import ch.ergon.dope.resolvable.expression.type.function.date.clockString
import ch.ergon.dope.resolvable.expression.type.function.date.dateRangeBy
import ch.ergon.dope.resolvable.expression.type.function.date.differenceIn
import ch.ergon.dope.resolvable.expression.type.function.date.extractDateComponent
import ch.ergon.dope.resolvable.expression.type.function.date.extractWeekdayName
import ch.ergon.dope.resolvable.expression.type.function.date.formatDate
import ch.ergon.dope.resolvable.expression.type.function.date.formattedClockIn
import ch.ergon.dope.resolvable.expression.type.function.date.localClockString
import ch.ergon.dope.resolvable.expression.type.function.date.localNowString
import ch.ergon.dope.resolvable.expression.type.function.date.nowEpochMillis
import ch.ergon.dope.resolvable.expression.type.function.date.nowString
import ch.ergon.dope.resolvable.expression.type.function.date.nowStringInZone
import ch.ergon.dope.resolvable.expression.type.function.date.toEpochMillis
import ch.ergon.dope.resolvable.expression.type.function.date.toFormattedDate
import ch.ergon.dope.resolvable.expression.type.function.date.toMillis
import ch.ergon.dope.resolvable.expression.type.function.date.toTimeZone
import ch.ergon.dope.resolvable.expression.type.function.date.toUtcDate
import ch.ergon.dope.resolvable.expression.type.function.date.truncateTo
import ch.ergon.dope.resolvable.expression.type.function.date.utcClockString
import ch.ergon.dope.resolvable.expression.type.function.date.utcNowString
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DateFunctionIntegrationTest : BaseIntegrationTest() {
    private fun computeForAlice(aliasedExpression: ch.ergon.dope.resolvable.Selectable) =
        executeQuery(
            QueryBuilder
                .select(aliasedExpression)
                .from(users)
                .where(name.isEqualTo("Alice Brown"))
                .buildMongo(resolver),
        )

    private val isoDateTimePattern = Regex("""\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}Z""")

    @Test
    fun `clock millis is close to system clock`() {
        val before = System.currentTimeMillis()
        val result = computeForAlice(clockMillis().alias("result"))
        val after = System.currentTimeMillis()

        val millis = (result[0]["result"] as Number).toLong()
        assertTrue(millis in (before - 5000)..(after + 5000), "clock millis $millis not near now")
    }

    @Test
    fun `now millis is close to system clock`() {
        val before = System.currentTimeMillis()
        val result = computeForAlice(nowEpochMillis().alias("result"))
        val after = System.currentTimeMillis()

        val millis = (result[0]["result"] as Number).toLong()
        assertTrue(millis in (before - 5000)..(after + 5000), "now millis $millis not near now")
    }

    @Test
    fun `local clock string renders default ISO`() {
        val result = computeForAlice(localClockString().alias("result"))
        assertTrue(isoDateTimePattern.matches(result[0]["result"] as String))
    }

    @Test
    fun `clock string renders default ISO`() {
        val result = computeForAlice(clockString().alias("result"))
        assertTrue(isoDateTimePattern.matches(result[0]["result"] as String))
    }

    @Test
    fun `utc clock string renders default ISO`() {
        val result = computeForAlice(utcClockString().alias("result"))
        assertTrue(isoDateTimePattern.matches(result[0]["result"] as String))
    }

    @Test
    fun `formatted clock in timezone yields two-digit hour`() {
        val result = computeForAlice(formattedClockIn("UTC", "%H").alias("result"))
        assertEquals(2, (result[0]["result"] as String).length)
    }

    @Test
    fun `local now string renders default ISO`() {
        val result = computeForAlice(localNowString().alias("result"))
        assertTrue(isoDateTimePattern.matches(result[0]["result"] as String))
    }

    @Test
    fun `now string with format matches todays UTC date`() {
        val expected = Instant.now().toString().substring(0, 10)
        val result = computeForAlice(nowString("%Y-%m-%d").alias("result"))
        assertEquals(expected, result[0]["result"])
    }

    @Test
    fun `utc now string renders default ISO`() {
        val result = computeForAlice(utcNowString().alias("result"))
        assertTrue(isoDateTimePattern.matches(result[0]["result"] as String))
    }

    @Test
    fun `now string in zone yields two-digit hour`() {
        val result = computeForAlice(nowStringInZone("Europe/Zurich", "%H").alias("result"))
        assertEquals(2, (result[0]["result"] as String).length)
    }

    @Test
    fun `date add millis adds two months`() {
        val result = computeForAlice(1609459200000L.addDateUnit(2, MONTH).alias("result"))
        assertEquals(1614556800000L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `date add str adds one day`() {
        val result = computeForAlice("2021-01-01T00:00:00Z".addDateUnit(1, DAY).alias("result"))
        assertTrue((result[0]["result"] as String).startsWith("2021-01-02"))
    }

    @Test
    fun `date diff millis in months`() {
        val result = computeForAlice(1612137600000L.differenceIn(1609459200000L, MONTH).alias("result"))
        assertEquals(1L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `date diff str in days`() {
        val result = computeForAlice("2021-02-01T00:00:00Z".differenceIn("2021-01-01T00:00:00Z", DAY).alias("result"))
        assertEquals(31L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `date format str re-renders date`() {
        val result = computeForAlice("2021-01-01T00:00:00Z".formatDate("%Y/%m/%d").alias("result"))
        assertEquals("2021/01/01", result[0]["result"])
    }

    @Test
    fun `date part millis extracts month`() {
        val result = computeForAlice(1609502400000L.extractDateComponent(MONTH).alias("result"))
        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `date part millis extracts day of week`() {
        val result = computeForAlice(1609459200000L.extractDateComponent(DAY_OF_WEEK).alias("result"))
        assertEquals(6, result[0]["result"])
    }

    @Test
    fun `date part str extracts year`() {
        val result = computeForAlice("2021-03-15T00:00:00Z".extractDateComponent(YEAR).alias("result"))
        assertEquals(2021, result[0]["result"])
    }

    @Test
    fun `date part str extracts day of year`() {
        val result = computeForAlice("2021-01-10T00:00:00Z".extractDateComponent(DAY_OF_YEAR).alias("result"))
        assertEquals(10, result[0]["result"])
    }

    @Test
    fun `date part str extracts iso week`() {
        val result = computeForAlice("2021-01-04T00:00:00Z".extractDateComponent(ISO_WEEK).alias("result"))
        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `date trunc millis truncates to month`() {
        val result = computeForAlice(1615852800000L.truncateTo(MONTH).alias("result"))
        assertEquals(1614556800000L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `date trunc str truncates to day`() {
        val result = computeForAlice("2021-03-16T10:00:00Z".truncateTo(DAY).alias("result"))
        assertTrue((result[0]["result"] as String).startsWith("2021-03-16T00:00:00"))
    }

    @Test
    fun `millis parses iso string to epoch millis`() {
        val result = computeForAlice("1970-01-01T00:00:01Z".toMillis().alias("result"))
        assertEquals(1000L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `string to millis parses iso string`() {
        val result = computeForAlice("2021-01-01T00:00:00Z".toEpochMillis().alias("result"))
        assertEquals(1609459200000L, (result[0]["result"] as Number).toLong())
    }

    @Test
    fun `millis to string renders default ISO`() {
        val result = computeForAlice(1000.toFormattedDate().alias("result"))
        assertEquals("1970-01-01T00:00:01.000Z", result[0]["result"])
    }

    @Test
    fun `millis to string with format`() {
        val result = computeForAlice(1609459200000L.toFormattedDate("%Y-%m-%d").alias("result"))
        assertEquals("2021-01-01", result[0]["result"])
    }

    @Test
    fun `millis to timezone renders local time`() {
        val result = computeForAlice(1609459200000L.toTimeZone("America/New_York", "%Y-%m-%d %H").alias("result"))
        assertEquals("2020-12-31 19", result[0]["result"])
    }

    @Test
    fun `str to timezone re-renders in zone`() {
        val result = computeForAlice("2021-01-01T00:00:00Z".toTimeZone("Europe/Zurich").alias("result"))
        assertTrue((result[0]["result"] as String).startsWith("2021-01-01T01:00:00"))
    }

    @Test
    fun `millis to utc renders utc string`() {
        val result = computeForAlice(1609459200000L.toUtcDate().alias("result"))
        assertEquals("2021-01-01T00:00:00.000Z", result[0]["result"])
    }

    @Test
    fun `str to utc normalizes offset to utc`() {
        val result = computeForAlice("2021-01-01T01:00:00+01:00".toUtcDate().alias("result"))
        assertEquals("2021-01-01T00:00:00.000Z", result[0]["result"])
    }

    @Test
    fun `date range millis steps by day`() {
        val result = computeForAlice(1609459200000L.dateRangeBy(1609718400000L, DAY, 1).alias("result"))
        val millis = (result[0]["result"] as List<*>).map { (it as Number).toLong() }
        assertEquals(listOf(1609459200000L, 1609545600000L, 1609632000000L), millis)
    }

    @Test
    fun `date range str steps by day`() {
        val result = computeForAlice(
            "2021-01-01T00:00:00Z".dateRangeBy("2021-01-04T00:00:00Z", DAY, 1).alias("result"),
        )
        val dates = (result[0]["result"] as List<*>).map { it as String }
        assertEquals(3, dates.size)
        assertTrue(dates[0].startsWith("2021-01-01"))
        assertTrue(dates[1].startsWith("2021-01-02"))
        assertTrue(dates[2].startsWith("2021-01-03"))
    }

    @Test
    fun `week day millis names friday`() {
        val result = computeForAlice(1609459200000L.extractWeekdayName().alias("result"))
        assertEquals("Friday", result[0]["result"])
    }

    @Test
    fun `week day str names friday`() {
        val result = computeForAlice("2021-01-01T00:00:00Z".extractWeekdayName().alias("result"))
        assertEquals("Friday", result[0]["result"])
    }
}
