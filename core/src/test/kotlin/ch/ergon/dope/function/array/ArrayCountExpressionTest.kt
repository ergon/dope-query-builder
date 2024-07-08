package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayCountExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayCount
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayCountExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_COUNT`() {
        val expected = DopeQuery(
            "ARRAY_COUNT(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayCountExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_COUNT($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayCountExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT extension`() {
        val array = someNumberArrayField()
        val expected = ArrayCountExpression(array)

        val actual = arrayCount(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
