package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayInsertExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayInsert
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayInsertExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_INSERT`() {
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, 1, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INSERT($1, 1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, $1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with third parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, 1, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with first and second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_INSERT($1, $2, `numberField`)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), parameterValue2.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with first and third parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_INSERT($1, 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            "ARRAY_INSERT($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT extension`() {
        val array = someNumberArrayField()
        val position = 1
        val value = someNumberField()
        val expected = ArrayInsertExpression(array, position.toDopeType(), value)

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
