package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.TypeOfExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.typeOf
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeOfExpressionTest : ParameterDependentTest {
    @Test
    fun `should support type of expression`() {
        val expected = DopeQuery(
            "TYPE(`stringField`)",
            emptyMap(),
        )
        val underTest = TypeOfExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support type of expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TYPE($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = TypeOfExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support type of extension`() {
        val string = someString().toDopeType()
        val expected = TypeOfExpression(string)

        val actual = typeOf(string)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
