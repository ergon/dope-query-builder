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

class DateDiffStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_DIFF_STR with fields`() {
        val expected = DopeQuery(
            queryString = "DATE_DIFF_STR(`stringField`, `stringField`, \"MONTH\")",
        )
        val underTest = DateDiffStrExpression(
            someStringField(),
            someStringField(),
            DateComponent.MONTH,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_DIFF_STR with positional parameter other`() {
        val otherValue = "2021-01-02T00:00:00Z"
        val expected = DopeQuery(
            queryString = "DATE_DIFF_STR(`stringField`, $1, \"DAY\")",
            DopeParameters(positionalParameters = listOf(otherValue)),
        )
        val underTest = DateDiffStrExpression(
            someStringField(),
            otherValue.asParameter(),
            DateComponent.DAY,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_DIFF_STR with named parameter date`() {
        val dateValue = "2021-01-01T00:00:00Z"
        val name = "d"
        val expected = DopeQuery(
            queryString = "DATE_DIFF_STR(\$$name, `stringField`, \"YEAR\")",
            DopeParameters(namedParameters = mapOf(name to dateValue)),
        )
        val underTest = DateDiffStrExpression(
            dateValue.asParameter(name),
            someStringField(),
            DateComponent.YEAR,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support differenceIn extension on TypeExpression`() {
        val expr = someStringField().differenceIn(someStringField(), DateComponent.QUARTER)
        val expected = DateDiffStrExpression(someStringField(), someStringField(), DateComponent.QUARTER)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support differenceIn extension on TypeExpression with raw`() {
        val raw = "2020-06-01T00:00:00Z"
        val expr = someStringField().differenceIn(raw, DateComponent.QUARTER)
        val expected = DateDiffStrExpression(someStringField(), raw.toDopeType(), DateComponent.QUARTER)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String differenceIn extension`() {
        val raw = "2020-06-01T00:00:00Z"
        val expr = raw.differenceIn(someStringField(), DateComponent.WEEK)
        val expected = DateDiffStrExpression(raw.toDopeType(), someStringField(), DateComponent.WEEK)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support differenceIn extension on Raw and raw`() {
        val date = "2020-06-01T00:00:00Z"
        val other = "2023-10-05T00:00:00Z"
        val expr = date.differenceIn(other, DateComponent.QUARTER)
        val expected = DateDiffStrExpression(date.toDopeType(), other.toDopeType(), DateComponent.QUARTER)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
