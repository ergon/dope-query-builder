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

class ClockLocalExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support CLOCK_LOCAL without format`() {
        val expected = DopeQuery(
            queryString = "CLOCK_LOCAL()",
        )
        val underTest = ClockLocalExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_LOCAL with format field`() {
        val formatField = someStringField()
        val expected = DopeQuery(
            queryString = "CLOCK_LOCAL(`stringField`)",
        )
        val underTest = ClockLocalExpression(formatField)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_LOCAL with positional parameter format`() {
        val parameterValue = "yyyy-MM-dd"
        val expected = DopeQuery(
            queryString = "CLOCK_LOCAL($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ClockLocalExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_LOCAL with named parameter format`() {
        val parameterValue = "yyyy-MM-dd"
        val parameterName = "fmt"
        val expected = DopeQuery(
            queryString = "CLOCK_LOCAL(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ClockLocalExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support localClockString extension with field`() {
        val formatField = someStringField()
        val expected = ClockLocalExpression(formatField)
        val actual = localClockString(formatField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support localClockString extension with raw string`() {
        val format = someString()
        val expected = ClockLocalExpression(format.toDopeType())
        val actual = localClockString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
