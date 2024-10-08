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
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INTERSECT($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayIntersectExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INTERSECT(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayIntersectExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_INTERSECT($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayIntersectExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

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
