package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.HOUR
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.YEAR
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateAddMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_ADD_MILLIS with fields`() {
        val expected = DopeQuery(
            queryString = "DATE_ADD_MILLIS(`numberField`, `numberField`, \"DAY\")",
        )
        val underTest = DateAddMillisExpression(
            someNumberField(),
            someNumberField(),
            DAY,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_ADD_MILLIS with positional parameter date`() {
        val dateValue = 1620000000000L
        val expected = DopeQuery(
            queryString = "DATE_ADD_MILLIS($1, `numberField`, \"DAY\")",
            DopeParameters(positionalParameters = listOf(dateValue)),
        )
        val underTest = DateAddMillisExpression(
            dateValue.asParameter(),
            someNumberField(),
            DAY,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_ADD_MILLIS with named parameter increment`() {
        val incValue = 5
        val name = "inc"
        val expected = DopeQuery(
            queryString = "DATE_ADD_MILLIS(`numberField`, \$$name, \"DAY\")",
            DopeParameters(namedParameters = mapOf(name to incValue)),
        )
        val underTest = DateAddMillisExpression(
            someNumberField(),
            incValue.asParameter(name),
            DAY,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support plusDateComponent extension on TypeExpression`() {
        val array = someNumberField()
        val underTest = array.addDateUnit(someNumberField(), HOUR)
        val expected = DateAddMillisExpression(array, someNumberField(), HOUR)

        assertEquals(expected.toDopeQuery(manager), underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support plusDateComponent extension with raw number increment`() {
        val array = someNumberField()
        val underTest = array.addDateUnit(10, MONTH)
        val expected = DateAddMillisExpression(array, 10.toDopeType(), MONTH)

        assertEquals(expected.toDopeQuery(manager), underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support Number plusDateComponent extension`() {
        val underTest = 15.addDateUnit(someNumberField(), YEAR)
        val expected = DateAddMillisExpression(15.toDopeType(), someNumberField(), YEAR)

        assertEquals(expected.toDopeQuery(manager), underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support Number Number plusDateComponent extension`() {
        val otherDate = someNumber()
        val underTest = 15.addDateUnit(otherDate, YEAR)
        val expected = DateAddMillisExpression(15.toDopeType(), otherDate.toDopeType(), YEAR)

        assertEquals(expected.toDopeQuery(manager), underTest.toDopeQuery(manager))
    }
}
