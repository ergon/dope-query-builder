package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayConcatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_CONCAT`() {
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, `numberArrayField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_CONCAT(\$$parameterName, `numberArrayField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayConcatExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with positional parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_CONCAT(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayConcatExpression(
            parameterValueCollection.asParameter(parameterName1),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT extension`() {
        val array = someNumberArrayField()
        val value = someNumberArrayField()
        val expected = ArrayConcatExpression(array, value)

        val actual = arrayConcat(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
