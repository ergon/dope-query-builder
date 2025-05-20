package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ClockTzExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support CLOCK_TZ with timezone field`() {
        val tzField = someStringField()
        val expected = DopeQuery(
            queryString = "CLOCK_TZ(`stringField`)",
        )
        val underTest = ClockTzExpression(tzField)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_TZ with positional parameter timezone`() {
        val tz = "UTC"
        val expected = DopeQuery(
            queryString = "CLOCK_TZ($1)",
            DopeParameters(positionalParameters = listOf(tz)),
        )
        val underTest = ClockTzExpression(tz.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_TZ with named parameter timezone`() {
        val tz = "UTC"
        val name = "zone"
        val expected = DopeQuery(
            queryString = "CLOCK_TZ(\$$name)",
            DopeParameters(namedParameters = mapOf(name to tz)),
        )
        val underTest = ClockTzExpression(tz.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_TZ with timezone and format field`() {
        val tzField = someStringField()
        val fmtField = someStringField()
        val expected = DopeQuery(
            queryString = "CLOCK_TZ(`stringField`, `stringField`)",
        )
        val underTest = ClockTzExpression(tzField, fmtField)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support formattedClockIn extension with raw args`() {
        val tz = someString()
        val fmt = someString()
        val expected = ClockTzExpression(tz.toDopeType(), fmt.toDopeType())
        val actual = formattedClockIn(tz, fmt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support formattedClockIn extension with raw timezone`() {
        val tz = someString()
        val expected = ClockTzExpression(tz.toDopeType())
        val actual = formattedClockIn(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support formattedClockIn extension with type timezone and raw format`() {
        val tz = someStringField()
        val fmt = someString()
        val expected = ClockTzExpression(tz, fmt.toDopeType())
        val actual = formattedClockIn(tz, fmt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
