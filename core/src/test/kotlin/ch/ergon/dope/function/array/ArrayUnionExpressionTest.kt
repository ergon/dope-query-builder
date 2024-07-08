package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayUnionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayUnion
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayUnionExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_UNION`() {
        val expected = DopeQuery(
            "ARRAY_UNION(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_UNION($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayUnionExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_UNION(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_UNION($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArrayUnionExpression(array, value)

        val actual = arrayUnion(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
