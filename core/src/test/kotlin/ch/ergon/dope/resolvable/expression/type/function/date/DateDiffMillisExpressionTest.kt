package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateDiffMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_DIFF_MILLIS with fields`() {
        val expected = DopeQuery(
            queryString = "DATE_DIFF_MILLIS(`numberField`, `numberField`, \"DAY\")",
        )
        val underTest = DateDiffMillisExpression(
            someNumberField(),
            someNumberField(),
            Day,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_DIFF_MILLIS with positional parameter other`() {
        val otherValue = 1000L
        val expected = DopeQuery(
            queryString = "DATE_DIFF_MILLIS(`numberField`, $1, \"HOUR\")",
            DopeParameters(positionalParameters = listOf(otherValue)),
        )
        val underTest = DateDiffMillisExpression(
            someNumberField(),
            otherValue.asParameter(),
            Hour,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_DIFF_MILLIS with named parameter date`() {
        val dateValue = 2000L
        val name = "d"
        val expected = DopeQuery(
            queryString = "DATE_DIFF_MILLIS(\$$name, `numberField`, \"MINUTE\")",
            DopeParameters(namedParameters = mapOf(name to dateValue)),
        )
        val underTest = DateDiffMillisExpression(
            dateValue.asParameter(name),
            someNumberField(),
            Minute,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support differenceIn extension on TypeExpression`() {
        val expr = someNumberField().differenceIn(someNumberField(), Second)
        val expected = DateDiffMillisExpression(someNumberField(), someNumberField(), Second)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support differenceIn extension on TypeExpression and Number`() {
        val raw = someNumber()
        val expr = someNumberField().differenceIn(raw, Second)
        val expected = DateDiffMillisExpression(someNumberField(), raw.toDopeType(), Second)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support differenceIn extension on Number and Number`() {
        val raw = someNumber(9)
        val expr = someNumber().differenceIn(raw, Second)
        val expected = DateDiffMillisExpression(someNumber().toDopeType(), raw.toDopeType(), Second)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number differenceIn extension`() {
        val expr = 5000L.differenceIn(someNumberField(), Millisecond)
        val expected = DateDiffMillisExpression(5000L.toDopeType(), someNumberField(), Millisecond)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
