package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArraySumExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArraySumExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_SUM`() {
        val expected = DopeQuery(
            "ARRAY_SUM(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArraySumExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SUM($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySumExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM extension`() {
        val array = someNumberArrayField()
        val expected = ArraySumExpression(array)

        val actual = arraySum(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
