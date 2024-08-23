package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.InitCapExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.TitleExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.initCap
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.title
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InitCapExpressionTest : ParameterDependentTest {
    @Test
    fun `should support init cap`() {
        val expected = DopeQuery(
            "INITCAP(`stringField`)",
            emptyMap(),
        )
        val underTest = InitCapExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "INITCAP($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = InitCapExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap function type`() {
        val inStr = someStringField("inStr")
        val expected = InitCapExpression(inStr)

        val actual = initCap(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support init cap function string`() {
        val inStr = someString()
        val expected = InitCapExpression(inStr.toDopeType())

        val actual = initCap(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support title function type`() {
        val inStr = someStringField("inStr")
        val expected = TitleExpression(inStr)

        val actual = title(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support title function string`() {
        val inStr = someString()
        val expected = TitleExpression(inStr.toDopeType())

        val actual = title(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
