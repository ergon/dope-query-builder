
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

class WeekDayStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support WEEKDAY_STR with field`() {
        val expected = DopeQuery(
            queryString = "WEEKDAY_STR(`stringField`)",
        )
        val underTest = WeekDayStrExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WEEKDAY_STR with positional parameter date`() {
        val date = "2021-07-07"
        val expected = DopeQuery(
            queryString = "WEEKDAY_STR($1)",
            DopeParameters(positionalParameters = listOf(date)),
        )
        val underTest = WeekDayStrExpression(date.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WEEKDAY_STR with named parameter date`() {
        val date = "2021-08-08"
        val name = "d"
        val expected = DopeQuery(
            queryString = "WEEKDAY_STR(\$$name)",
            DopeParameters(namedParameters = mapOf(name to date)),
        )
        val underTest = WeekDayStrExpression(date.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extractWeekdayName extension on TypeExpression`() {
        val expr = someStringField().extractWeekdayName()
        val expected = WeekDayStrExpression(someStringField())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String extractWeekdayName extension`() {
        val raw = someString()
        val expr = raw.extractWeekdayName()
        val expected = WeekDayStrExpression(raw.toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
