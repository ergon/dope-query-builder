package ch.ergon.dope.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayReplaceExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayReplace
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayReplaceExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_REPLACE`() {
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, 1, 2)",
            emptyMap(),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, 1, 2)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, $1, 2)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), parameterValue.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with third parameter`() {
        val parameterValue = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE(`numberArrayField`, 1, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayReplaceExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, $2, 2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 2.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with first and third parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE with all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            "ARRAY_REPLACE($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = ArrayReplaceExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE extension`() {
        val array = someNumberArrayField()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith)

        val actual = arrayReplace(array, toReplace, replaceWith)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_REPLACE extension with max`() {
        val array = someNumberArrayField()
        val toReplace = someNumberField()
        val replaceWith = someNumberField("anotherNumberField")
        val max = 1.toDopeType()
        val expected = ArrayReplaceExpression(array, toReplace, replaceWith, max)

        val actual = arrayReplace(array, toReplace, replaceWith, max)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
