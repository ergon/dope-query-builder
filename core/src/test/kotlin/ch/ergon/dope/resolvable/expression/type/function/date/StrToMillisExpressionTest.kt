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

class StrToMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support STR_TO_MILLIS with field only`() {
        val underTest = StringToMillisExpression(someStringField())
        val expected = DopeQuery("STR_TO_MILLIS(`stringField`)")
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support STR_TO_MILLIS with positional format parameter`() {
        val underTest = StringToMillisExpression(someStringField(), "yyyy".asParameter())
        val expected = DopeQuery("STR_TO_MILLIS(`stringField`, $1)", DopeParameters(positionalParameters = listOf("yyyy")))
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support STR_TO_MILLIS with named format parameter`() {
        val underTest = StringToMillisExpression(someStringField(), "fmt".asParameter("f"))
        val expected = DopeQuery("STR_TO_MILLIS(`stringField`, \$f)", DopeParameters(namedParameters = mapOf("f" to "fmt")))
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support toEpochMillis extension on TypeExpression without format`() {
        val expr = someStringField().toEpochMillis()
        val expected = StringToMillisExpression(someStringField(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toEpochMillis extension on TypeExpression with format expression`() {
        val fmtExpr = someStringField()
        val expr = someStringField().toEpochMillis(fmtExpr)
        val expected = StringToMillisExpression(someStringField(), fmtExpr)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support toEpochMillis extension on TypeExpression with raw format`() {
        val expr = someStringField().toEpochMillis("MM-dd")
        val expected = StringToMillisExpression(someStringField(), "MM-dd".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toEpochMillis extension without format`() {
        val raw = someString()
        val expr = raw.toEpochMillis()
        val expected = StringToMillisExpression(raw.toDopeType(), null)
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toEpochMillis extension with format expression`() {
        val raw = someString()
        val expr = raw.toEpochMillis(someStringField())
        val expected = StringToMillisExpression(raw.toDopeType(), someStringField())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toEpochMillis extension with raw format`() {
        val raw = someString()
        val expr = raw.toEpochMillis("yy")
        val expected = StringToMillisExpression(raw.toDopeType(), "yy".toDopeType())
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
