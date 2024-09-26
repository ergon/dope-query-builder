package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayContainsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_CONTAINS`() {
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, `numberField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_CONTAINS(\$$parameterName, `numberField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayContainsExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_CONTAINS(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayContainsExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_CONTAINS($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayContainsExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_CONTAINS(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayContainsExpression(
            parameterValueCollection.asParameter(parameterName1),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS extension`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayContainsExpression(array, value)

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension number`() {
        val array = someNumberArrayField()
        val value = 1
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension string`() {
        val array = someStringArrayField()
        val value = "s"
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS extension boolean`() {
        val array = someBooleanArrayField()
        val value = true
        val expected = ArrayContainsExpression(array, value.toDopeType())

        val actual = arrayContains(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
