package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayDistinctExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_DISTINCT`() {
        val expected = DopeQuery(
            "ARRAY_DISTINCT(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayDistinctExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_DISTINCT($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayDistinctExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT extension`() {
        val array = someNumberArrayField()
        val expected = ArrayDistinctExpression(array)

        val actual = arrayDistinct(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
