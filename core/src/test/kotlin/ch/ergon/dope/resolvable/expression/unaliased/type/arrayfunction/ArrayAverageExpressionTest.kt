package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayAverageExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_AVG`() {
        val expected = DopeQuery(
            "ARRAY_AVG(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayAverageExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_AVG with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_AVG($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayAverageExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_AVG extension`() {
        val array = someNumberArrayField()
        val expected = ArrayAverageExpression(array)

        val actual = arrayAverage(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
