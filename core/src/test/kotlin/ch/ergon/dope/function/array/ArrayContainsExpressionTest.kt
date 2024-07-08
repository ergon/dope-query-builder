package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayContainsExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_CONTAINS`() {
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayContainsExpression(array, value)

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_CONTAINS extension number`() {
        val array = someNumberArrayField()
        val value = 1
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_CONTAINS extension string`() {
        val array = someStringArrayField()
        val value = "s"
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_CONTAINS extension boolean`() {
        val array = someBooleanArrayField()
        val value = true
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
