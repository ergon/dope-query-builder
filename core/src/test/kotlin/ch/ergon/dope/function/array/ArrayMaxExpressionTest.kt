package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayMaxExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayMax
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayMaxExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_MAX`() {
        val expected = DopeQuery(
            "ARRAY_MAX(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayMaxExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_MAX($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX extension`() {
        val array = someNumberArrayField()
        val expected = ArrayMaxExpression(array)

        val actual = arrayMax(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
