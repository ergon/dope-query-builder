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

class StrToUtcExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support STR_TO_UTC with field`() {
        val expected = DopeQuery(
            queryString = "STR_TO_UTC(`stringField`)",
        )
        val underTest = StrToUtcExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_UTC with positional parameter date`() {
        val date = "2021-01-01T00:00:00Z"
        val expected = DopeQuery(
            queryString = "STR_TO_UTC($1)",
            DopeParameters(positionalParameters = listOf(date)),
        )
        val underTest = StrToUtcExpression(date.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_UTC with named parameter date`() {
        val date = "2022-02-02T02:02:02Z"
        val name = "d"
        val expected = DopeQuery(
            queryString = "STR_TO_UTC(\$$name)",
            DopeParameters(namedParameters = mapOf(name to date)),
        )
        val underTest = StrToUtcExpression(date.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toUtcDate extension on TypeExpression`() {
        val expr = someStringField().toUtcDate()
        val expected = StrToUtcExpression(someStringField())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toUtcDate extension`() {
        val raw = someString()
        val expr = raw.toUtcDate()
        val expected = StrToUtcExpression(raw.toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
