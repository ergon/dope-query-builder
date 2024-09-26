package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFlattenExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_FLATTEN`() {
        val expected = DopeQuery(
            "ARRAY_FLATTEN(`numberArrayField`, `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_FLATTEN($1, `numberField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayFlattenExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_FLATTEN(\$$parameterName, `numberField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayFlattenExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_FLATTEN(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_FLATTEN(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_FLATTEN($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayFlattenExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_FLATTEN(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayFlattenExpression(
            parameterValueCollection.asParameter(parameterName1),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN extension`() {
        val array = someNumberArrayField()
        val depth = someNumberField()
        val expected = ArrayFlattenExpression(array, depth)

        val actual = arrayFlatten(array, depth)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_FLATTEN extension with number`() {
        val array = someNumberArrayField()
        val depth = 1
        val expected = ArrayFlattenExpression(array, depth.toDopeType())

        val actual = arrayFlatten(array, depth)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
