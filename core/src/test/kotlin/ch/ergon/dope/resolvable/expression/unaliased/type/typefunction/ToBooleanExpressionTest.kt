package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToBooleanExpressionTest : ParameterDependentTest {
    @Test
    fun `should support to boolean expression`() {
        val expected = DopeQuery(
            "TOBOOLEAN(`stringField`)",
            emptyMap(),
        )
        val underTest = ToBooleanExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TOBOOLEAN($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ToBooleanExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean extension`() {
        val boolean = someString()
        val expected = ToBooleanExpression(boolean.toDopeType())

        val actual = toBoolean(boolean.toDopeType())

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
