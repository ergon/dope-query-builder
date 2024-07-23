package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArraySymmetricDifferenceTest : ParameterDependentTest {
    @Test
    fun `should support ARRAY_SYMDIFF`() {
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(array, value)

        val actual = arraySymDiff(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(array, value)

        val actual = arraySymDiff1(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(array, value)

        val actual = arraySymDiffN(array, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
