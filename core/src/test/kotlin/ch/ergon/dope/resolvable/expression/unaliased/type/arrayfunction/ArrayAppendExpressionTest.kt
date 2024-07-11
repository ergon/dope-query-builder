package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayAppendExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_APPEND`() {
        val expected = DopeQuery(
            "ARRAY_APPEND(`numberArrayField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayAppendExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_APPEND with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_APPEND($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayAppendExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_APPEND with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_APPEND(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayAppendExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_APPEND with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_APPEND($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayAppendExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_APPEND extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayAppendExpression(array, value)

        val actual = arrayAppend(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
