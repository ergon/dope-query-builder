package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayUnionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_UNION`() {
        val expected = DopeQuery(
            "ARRAY_UNION(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_UNION($1, `numberArrayField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayUnionExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with positional parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_UNION(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_UNION($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_UNION(\$$parameterName, `numberArrayField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayUnionExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_UNION(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName1 = "param1"
        val parameterValue = listOf(4, 5, 6)
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_UNION(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(parameterName1), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with mixed named and positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param"
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_UNION(\$$parameterName, $1)",
            mapOf(parameterName to parameterValueCollection),
            listOf(parameterValue),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArrayUnionExpression(array, value)

        val actual = arrayUnion(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
