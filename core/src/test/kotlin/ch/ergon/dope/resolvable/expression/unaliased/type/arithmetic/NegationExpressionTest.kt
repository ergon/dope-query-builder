package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationExpressionTest : ParameterDependentTest {
    @Test
    fun `should support negation`() {
        val expected = DopeQuery(
            "-`numberField`",
            emptyMap(),
        )
        val underTest = NegationExpression(someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "-$1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NegationExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation function type`() {
        val type = someNumberField()
        val expected = NegationExpression(type)

        val actual = neg(type)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support negation function number`() {
        val number = someNumber()
        val expected = NegationExpression(number.toDopeType())

        val actual = neg(number)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
