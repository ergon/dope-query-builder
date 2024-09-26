package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayIntersectExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_INTERSECT`() {
        val expected = DopeQuery(
            "ARRAY_INTERSECT(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INTERSECT($1, `numberArrayField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayIntersectExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_INTERSECT(\$$parameterName, `numberArrayField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayIntersectExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with positional second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INTERSECT(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with named second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_INTERSECT(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_INTERSECT($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayIntersectExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_INTERSECT(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayIntersectExpression(
            parameterValueCollection.asParameter(parameterName1),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArrayIntersectExpression(array, value)

        val actual = arrayIntersect(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
