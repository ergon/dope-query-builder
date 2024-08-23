package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArraySortExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArraySortExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_SORT`() {
        val expected = DopeQuery(
            "ARRAY_SORT(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArraySortExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SORT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SORT($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySortExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SORT extension`() {
        val array = someNumberArrayField()
        val expected = ArraySortExpression(array)

        val actual = arraySort(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
