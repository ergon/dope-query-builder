package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayPositionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayPosition
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayPositionExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_POSITION`() {
        val expected = DopeQuery(
            "ARRAY_POSITION(`numberArrayField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayPositionExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_POSITION($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPositionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_POSITION(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPositionExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_POSITION($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayPositionExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayPositionExpression(array, value)

        val actual = arrayPosition(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_POSITION extension number`() {
        val array = someNumberArrayField()
        val value = 1
        val expected = ArrayPositionExpression(array, value.toDopeType())

        val actual = arrayPosition(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
