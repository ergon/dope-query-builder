package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
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
            queryString = "ARRAY_INTERSECT(`numberArrayField`, `numberArrayField`)",
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_INTERSECT($1, `numberArrayField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
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
            queryString = "ARRAY_INTERSECT(\$$parameterName, `numberArrayField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayIntersectExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with positional second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_INTERSECT(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
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
            queryString = "ARRAY_INTERSECT(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
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
            queryString = "ARRAY_INTERSECT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayIntersectExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_INTERSECT(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest = ArrayIntersectExpression(
            parameterValueCollection.asParameter(parameterName),
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
