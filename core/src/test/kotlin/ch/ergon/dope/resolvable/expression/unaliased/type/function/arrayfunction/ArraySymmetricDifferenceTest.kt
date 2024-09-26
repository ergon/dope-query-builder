package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArraySymmetricDifferenceTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_SYMDIFF`() {
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, `numberArrayField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with positional parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(\$$parameterName, `numberArrayField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName1 = "param1"
        val parameterValue = listOf(4, 5, 6)
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest =
            ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(parameterName1), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with mixed named and positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param"
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(\$$parameterName, $1)",
            mapOf(parameterName to parameterValueCollection),
            listOf(parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(array, value)

        val actual = arraySymDiff(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(array, value)

        val actual = arraySymDiff1(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(array, value)

        val actual = arraySymDiffN(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
