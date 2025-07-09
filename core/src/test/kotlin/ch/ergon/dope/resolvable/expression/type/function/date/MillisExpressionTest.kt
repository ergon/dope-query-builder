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

class MillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MILLIS with field`() {
        val expected = DopeQuery(
            queryString = "MILLIS(`stringField`)",
        )
        val underTest = MillisExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support MILLIS with positional parameter date`() {
        val date = "2021-06-01T00:00:00Z"
        val expected = DopeQuery(
            queryString = "MILLIS($1)",
            DopeParameters(positionalParameters = listOf(date)),
        )
        val underTest = MillisExpression(date.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support MILLIS with named parameter date`() {
        val date = "2021-06-01T00:00:00Z"
        val name = "d"
        val expected = DopeQuery(
            queryString = "MILLIS(\$$name)",
            DopeParameters(namedParameters = mapOf(name to date)),
        )
        val underTest = MillisExpression(date.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toMillis extension on TypeExpression`() {
        val expr = someStringField().toMillis()
        val expected = MillisExpression(someStringField())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toMillis extension`() {
        val expr = "2022-01-01T12:00:00Z".toMillis()
        val expected = MillisExpression("2022-01-01T12:00:00Z".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
