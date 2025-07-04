package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.WEEK
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.YEAR
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateAddStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_ADD_STR with fields`() {
        val expected = DopeQuery(
            queryString = "DATE_ADD_STR(`stringField`, `numberField`, \"MONTH\")",
        )
        val underTest = DateAddStrExpression(
            someStringField(),
            someNumberField(),
            MONTH,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_ADD_STR with positional parameter date`() {
        val dateValue = "2021-01-01T00:00:00Z"
        val expected = DopeQuery(
            queryString = "DATE_ADD_STR($1, `numberField`, \"MONTH\")",
            DopeParameters(positionalParameters = listOf(dateValue)),
        )
        val underTest = DateAddStrExpression(
            dateValue.asParameter(),
            someNumberField(),
            MONTH,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_ADD_STR with named parameter increment`() {
        val incValue = 3
        val name = "qty"
        val expected = DopeQuery(
            queryString = "DATE_ADD_STR(`stringField`, \$$name, \"MONTH\")",
            DopeParameters(namedParameters = mapOf(name to incValue)),
        )
        val underTest = DateAddStrExpression(
            someStringField(),
            incValue.asParameter(name),
            MONTH,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support plusDateComponent extension on TypeExpression`() {
        val expr = someStringField().addDateUnit(someNumberField(), DAY)
        val expected = DateAddStrExpression(someStringField(), someNumberField(), DAY)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support plusDateComponent extension with raw number`() {
        val expr = someStringField().addDateUnit(7, WEEK)
        val expected = DateAddStrExpression(someStringField(), 7.toDopeType(), WEEK)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String plusDateComponent extension`() {
        val raw = "2020-05-05T05:05:05Z"
        val expr = raw.addDateUnit(1, YEAR)
        val expected = DateAddStrExpression(raw.toDopeType(), 1.toDopeType(), YEAR)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String with type plusDateComponent extension`() {
        val raw = "2020-05-05T05:05:05Z"
        val numberField = someNumberField()
        val expr = raw.addDateUnit(numberField, YEAR)
        val expected = DateAddStrExpression(raw.toDopeType(), numberField, YEAR)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
