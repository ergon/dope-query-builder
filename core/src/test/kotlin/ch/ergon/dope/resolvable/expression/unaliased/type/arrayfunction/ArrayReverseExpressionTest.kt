package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayReverseExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_REVERSE`() {
        val expected = DopeQuery(
            "ARRAY_REVERSE(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayReverseExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REVERSE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_REVERSE($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReverseExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REVERSE extension`() {
        val array = someNumberArrayField()
        val expected = ArrayReverseExpression(array)

        val actual = arrayReverse(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
