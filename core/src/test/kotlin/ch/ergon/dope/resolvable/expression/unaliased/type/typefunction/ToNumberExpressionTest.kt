package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToNumberExpressionTest : ParameterDependentTest {
    @Test
    fun `should support to number expression`() {
        val expected = DopeQuery(
            "TONUMBER(`stringField`)",
            emptyMap(),
        )
        val underTest = ToNumberExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TONUMBER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ToNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number extension`() {
        val number = someString()
        val expected = ToNumberExpression(number.toDopeType())

        val actual = toNumber(number.toDopeType())

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
