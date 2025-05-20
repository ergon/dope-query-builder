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

class ClockStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support CLOCK_STR without format`() {
        val expected = DopeQuery(
            queryString = "CLOCK_STR()",
        )
        val underTest = ClockStrExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_STR with format field`() {
        val fmt = someStringField()
        val expected = DopeQuery(
            queryString = "CLOCK_STR(`stringField`)",
        )
        val underTest = ClockStrExpression(fmt)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_STR with positional parameter format`() {
        val parameterValue = "MM-dd-yyyy"
        val expected = DopeQuery(
            queryString = "CLOCK_STR($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ClockStrExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_STR with named parameter format`() {
        val parameterValue = "MM-dd-yyyy"
        val parameterName = "fmt"
        val expected = DopeQuery(
            queryString = "CLOCK_STR(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ClockStrExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support clockString extension with field`() {
        val fmt = someStringField()
        val expected = ClockStrExpression(fmt)
        val actual = clockString(fmt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support clockString extension with raw string`() {
        val rawFmt = someString()
        val expected = ClockStrExpression(rawFmt.toDopeType())
        val actual = clockString(rawFmt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
