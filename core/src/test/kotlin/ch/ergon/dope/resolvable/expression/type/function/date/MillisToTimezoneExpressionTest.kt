package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MillisToTimezoneExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MILLIS_TO_TZ with tz field and no format`() {
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, `stringField`)",
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            someStringField(),
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_TZ with positional tz parameter`() {
        val tz = "UTC"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, $1)",
            DopeParameters(positionalParameters = listOf(tz)),
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            tz.asParameter(),
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_TZ with named tz parameter`() {
        val tz = "Europe/London"
        val name = "zone"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, \$$name)",
            DopeParameters(namedParameters = mapOf(name to tz)),
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            tz.asParameter(name),
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_TZ with tz and format fields`() {
        val tzField = someStringField()
        val fmtField = someStringField()
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, `stringField`, `stringField`)",
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            tzField,
            fmtField,
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_TZ with positional tz and positional format`() {
        val tz = "Asia/Tokyo"
        val fmt = "yyyy-MM-dd"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, $1, $2)",
            DopeParameters(positionalParameters = listOf(tz, fmt)),
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            tz.asParameter(),
            fmt.asParameter(),
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_TZ with named tz and named format`() {
        val tz = "America/New_York"
        val fmt = "MM/dd/yyyy"
        val tzName = "zone"
        val fmtName = "f"
        val expected = DopeQuery(
            queryString = "MILLIS_TO_TZ(`numberField`, \$$tzName, \$$fmtName)",
            DopeParameters(namedParameters = mapOf(tzName to tz, fmtName to fmt)),
        )
        val underTest = MillisToTimezoneExpression(
            someNumberField(),
            tz.asParameter(tzName),
            fmt.asParameter(fmtName),
        )

        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    // --- Extension function tests ---

    @Test
    fun `should support toFormattedDateIn extension with tz field and no format`() {
        val expr = someNumberField().toTimeZone(someStringField())
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            someStringField(),
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDateIn extension with raw tz and no format`() {
        val expr = someNumberField().toTimeZone("UTC")
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            "UTC".toDopeType(),
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDateIn extension with tz field and format field`() {
        val expr = someNumberField().toTimeZone(someStringField(), someStringField())
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            someStringField(),
            someStringField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDateIn extension with tz field and raw format`() {
        val expr = someNumberField().toTimeZone(someStringField(), "yyyy")
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            someStringField(),
            "yyyy".toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDateIn extension with raw tz and format field`() {
        val expr = someNumberField().toTimeZone("Asia/Kolkata", someStringField())
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            "Asia/Kolkata".toDopeType(),
            someStringField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toFormattedDateIn extension with raw tz and raw format`() {
        val expr = someNumberField().toTimeZone("Europe/Paris", "dd/MM")
        val expected = MillisToTimezoneExpression(
            someNumberField(),
            "Europe/Paris".toDopeType(),
            "dd/MM".toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with tz field`() {
        val expr = 1234L.toTimeZone(someStringField())
        val expected = MillisToTimezoneExpression(
            1234L.toDopeType(),
            someStringField(),
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with raw tz`() {
        val expr = 5678L.toTimeZone("UTC")
        val expected = MillisToTimezoneExpression(
            5678L.toDopeType(),
            "UTC".toDopeType(),
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with tz and format fields`() {
        val expr = 91011L.toTimeZone(someStringField(), someStringField())
        val expected = MillisToTimezoneExpression(
            91011L.toDopeType(),
            someStringField(),
            someStringField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with tz field and raw format`() {
        val expr = 1213L.toTimeZone(someStringField(), "yyyy")
        val expected = MillisToTimezoneExpression(
            1213L.toDopeType(),
            someStringField(),
            "yyyy".toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with raw tz and format field`() {
        val expr = 1415L.toTimeZone("America/Toronto", someStringField())
        val expected = MillisToTimezoneExpression(
            1415L.toDopeType(),
            "America/Toronto".toDopeType(),
            someStringField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateIn extension with raw tz and raw format`() {
        val expr = 1617L.toTimeZone("Asia/Tokyo", "MM-dd")
        val expected = MillisToTimezoneExpression(
            1617L.toDopeType(),
            "Asia/Tokyo".toDopeType(),
            "MM-dd".toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
