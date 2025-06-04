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

class DatePartStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_PART_STR with field`() {
        val expected = DopeQuery(
            queryString = "DATE_PART_STR(`stringField`, \"DAY\")",
        )
        val underTest = DatePartStrExpression(
            someStringField(),
            Day,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_PART_STR with positional parameter date`() {
        val dateValue = "2021-01-01T00:00:00Z"
        val expected = DopeQuery(
            queryString = "DATE_PART_STR($1, \"MONTH\")",
            DopeParameters(positionalParameters = listOf(dateValue)),
        )
        val underTest = DatePartStrExpression(
            dateValue.asParameter(),
            Month,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_PART_STR with named parameter date`() {
        val dateValue = "2021-01-01T00:00:00Z"
        val name = "d"
        val expected = DopeQuery(
            queryString = "DATE_PART_STR(\$$name, \"YEAR\")",
            DopeParameters(namedParameters = mapOf(name to dateValue)),
        )
        val underTest = DatePartStrExpression(
            dateValue.asParameter(name),
            Year,
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extractDateComponent extension on TypeExpression`() {
        val expr = someStringField().extractDateComponent(Week)
        val expected = DatePartStrExpression(someStringField(), Week)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String extractDateComponent extension`() {
        val raw = "2016-05-15T03:59:00Z"
        val expr = raw.extractDateComponent(DayOfYear)
        val expected = DatePartStrExpression(raw.toDopeType(), DayOfYear)

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
