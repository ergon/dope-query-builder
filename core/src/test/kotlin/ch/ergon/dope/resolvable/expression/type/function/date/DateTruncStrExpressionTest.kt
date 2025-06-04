package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTruncStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_TRUNC_STR with field`() {
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_STR(`stringField`, \"MONTH\")",
        )
        val underTest = DateTruncStrExpression(
            someStringField(),
            Month,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_TRUNC_STR with positional parameter date`() {
        val dateVal = "2021-12-31T23:59:59Z"
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_STR($1, \"YEAR\")",
            DopeParameters(positionalParameters = listOf(dateVal)),
        )
        val underTest = DateTruncStrExpression(
            dateVal.asParameter(),
            Year,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_TRUNC_STR with named parameter date`() {
        val dateVal = "2022-01-01T00:00:00Z"
        val name = "d"
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_STR(\$$name, \"WEEK\")",
            DopeParameters(namedParameters = mapOf(name to dateVal)),
        )
        val underTest = DateTruncStrExpression(
            dateVal.asParameter(name),
            Week,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support truncateTo extension on TypeExpression`() {
        val expr = someStringField().truncateTo(Day)
        val expected = DateTruncStrExpression(someStringField(), Day)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String truncateTo extension`() {
        val raw = "2020-05-05"
        val expr = raw.truncateTo(Month)
        val expected = DateTruncStrExpression(raw.toDopeType(), Month)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
