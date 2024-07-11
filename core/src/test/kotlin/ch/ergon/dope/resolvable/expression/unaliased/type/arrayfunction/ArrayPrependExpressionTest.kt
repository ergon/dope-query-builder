package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayPrependExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_PREPEND`() {
        val expected = DopeQuery(
            "ARRAY_PREPEND(`numberField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayPrependExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_PREPEND(`numberField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPrependExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PREPEND($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPrependExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PREPEND($2, $1)",
            mapOf("$2" to parameterValue, "$1" to parameterValueCollection),
        )
        val underTest = ArrayPrependExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayPrependExpression(array, value)

        val actual = arrayPrepend(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
