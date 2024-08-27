package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.LengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.length
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LengthExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support length`() {
        val expected = DopeQuery(
            "LENGTH(`stringField`)",
            emptyMap(),
        )
        val underTest = LengthExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LENGTH($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length function type`() {
        val inStr = someStringField("inStr")
        val expected = LengthExpression(inStr)

        val actual = length(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support length function string`() {
        val inStr = someString()
        val expected = LengthExpression(inStr.toDopeType())

        val actual = length(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
