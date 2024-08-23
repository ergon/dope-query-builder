package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayConcat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayConcatExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_CONCAT`() {
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArrayConcatExpression(array, value)

        val actual = arrayConcat(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
