package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayLengthExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_LENGTH`() {
        val expected = DopeQuery(
            "ARRAY_LENGTH(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayLengthExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_LENGTH($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayLengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH extension`() {
        val array = someNumberArrayField()
        val expected = ArrayLengthExpression(array)

        val actual = arrayLength(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
