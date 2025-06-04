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
            Month,
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
            Month,
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
            Month,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support plusDateComponent extension on TypeExpression`() {
        val expr = someStringField().plusDateComponent(someNumberField(), Day)
        val expected = DateAddStrExpression(someStringField(), someNumberField(), Day)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support plusDateComponent extension with raw number`() {
        val expr = someStringField().plusDateComponent(7, Week)
        val expected = DateAddStrExpression(someStringField(), 7.toDopeType(), Week)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String plusDateComponent extension`() {
        val raw = "2020-05-05T05:05:05Z"
        val expr = raw.plusDateComponent(1, Year)
        val expected = DateAddStrExpression(raw.toDopeType(), 1.toDopeType(), Year)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String with type plusDateComponent extension`() {
        val raw = "2020-05-05T05:05:05Z"
        val numberField = someNumberField()
        val expr = raw.plusDateComponent(numberField, Year)
        val expected = DateAddStrExpression(raw.toDopeType(), numberField, Year)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
