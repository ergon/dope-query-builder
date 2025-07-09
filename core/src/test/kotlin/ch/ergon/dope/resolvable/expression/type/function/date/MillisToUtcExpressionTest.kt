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

class MillisToUtcExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MILLIS_TO_UTC without format`() {
        val underTest = MillisToUtcExpression(someNumberField())
        val expected = DopeQuery("MILLIS_TO_UTC(`numberField`)")
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_UTC with positional parameter date`() {
        val date = 1620000000000L
        val underTest = MillisToUtcExpression(date.asParameter())
        val expected = DopeQuery("MILLIS_TO_UTC($1)", DopeParameters(positionalParameters = listOf(date)))
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support MILLIS_TO_UTC with named parameter date`() {
        val date = 1620000000001L
        val underTest = MillisToUtcExpression(date.asParameter("d"))
        val expected = DopeQuery("MILLIS_TO_UTC($" + "d)", DopeParameters(namedParameters = mapOf("d" to date)))
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support TypeExpression toFormattedDateInUtc extension without format`() {
        val expr = someNumberField().toUtcDate()
        val expected = MillisToUtcExpression(someNumberField(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support TypeExpression toFormattedDateInUtc extension with field format`() {
        val fmtExpr = someStringField()
        val expr = someNumberField().toUtcDate(fmtExpr)
        val expected = MillisToUtcExpression(someNumberField(), fmtExpr)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support TypeExpression toFormattedDateInUtc extension with raw string format`() {
        val expr = someNumberField().toUtcDate("yyyy")
        val expected = MillisToUtcExpression(someNumberField(), "yyyy".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateInUtc extension without format`() {
        val expr = 123L.toUtcDate()
        val expected = MillisToUtcExpression(123L.toDopeType(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateInUtc extension with field format`() {
        val fmtExpr = someStringField()
        val expr = 456L.toUtcDate(fmtExpr)
        val expected = MillisToUtcExpression(456L.toDopeType(), fmtExpr)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number toFormattedDateInUtc extension with raw string format`() {
        val expr = 789L.toUtcDate("MM-dd")
        val expected = MillisToUtcExpression(789L.toDopeType(), "MM-dd".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
