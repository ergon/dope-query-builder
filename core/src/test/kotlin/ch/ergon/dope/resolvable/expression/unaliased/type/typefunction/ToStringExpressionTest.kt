package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToStringExpressionTest : ParameterDependentTest {
    @Test
    fun `should support to string expression`() {
        val expected = DopeQuery(
            "TOSTRING(`stringField`)",
            emptyMap(),
        )
        val underTest = ToStringExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TOSTRING($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ToStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string extension`() {
        val string = someString()
        val expected = ToStringExpression(string.toDopeType())

        val actual = toString(string.toDopeType())

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
