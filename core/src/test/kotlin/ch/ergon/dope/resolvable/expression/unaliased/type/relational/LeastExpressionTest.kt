package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class LeastExpressionTest : ParameterDependentTest {
    @Test
    fun `should support greatest expression`() {
        val expected = DopeQuery(
            "LEAST(`numberField`, `anotherNumberField`)",
            emptyMap(),
        )
        val underTest = LeastExpression(someNumberField(), someNumberField("anotherNumberField"))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with first parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "LEAST($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LeastExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "LEAST(`numberField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LeastExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with all parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "LEAST($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = LeastExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression function`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val expected = LeastExpression(numberField, numberField2)

        val actual = leastOf(numberField, numberField2)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greatest expression function with additional expressions`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val numberField3 = someNumberField()
        val numberField4 = someNumberField()
        val expected = LeastExpression(numberField, numberField2, numberField3, numberField4)

        val actual = leastOf(numberField, numberField2, numberField3, numberField4)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
