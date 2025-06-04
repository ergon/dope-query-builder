package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTruncMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_TRUNC_MILLIS with field`() {
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_MILLIS(`numberField`, \"MONTH\")",
        )
        val underTest = DateTruncMillisExpression(
            someNumberField(),
            Month,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_TRUNC_MILLIS with positional parameter date`() {
        val dateVal = 1620000000000L
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_MILLIS($1, \"YEAR\")",
            DopeParameters(positionalParameters = listOf(dateVal)),
        )
        val underTest = DateTruncMillisExpression(
            dateVal.asParameter(),
            Year,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_TRUNC_MILLIS with named parameter date`() {
        val dateVal = 1620000000000L
        val name = "d"
        val expected = DopeQuery(
            queryString = "DATE_TRUNC_MILLIS(\$$name, \"WEEK\")",
            DopeParameters(namedParameters = mapOf(name to dateVal)),
        )
        val underTest = DateTruncMillisExpression(
            dateVal.asParameter(name),
            Week,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support truncateTo extension on TypeExpression`() {
        val expr = someNumberField().truncateTo(Millennium)
        val expected = DateTruncMillisExpression(someNumberField(), Millennium)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number truncateTo extension`() {
        val expr = 1620000000000L.truncateTo(Second)
        val expected = DateTruncMillisExpression(1620000000000L.toDopeType(), Second)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
