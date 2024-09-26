package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayPutExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_PUT`() {
        val expected = DopeQuery(
            "ARRAY_PUT(`numberArrayField`, `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ArrayPutExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_PUT(\$$parameterName, `numberField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayPutExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_PUT($1, `numberField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayPutExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            "ARRAY_PUT(`numberArrayField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ArrayPutExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PUT(`numberArrayField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ArrayPutExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "ARRAY_PUT(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValueCollection, parameterName2 to parameterValue),
            emptyList(),
        )
        val underTest = ArrayPutExpression(parameterValueCollection.asParameter(parameterName1), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PUT($1, $2)",
            emptyMap(),
            listOf(parameterValueCollection, parameterValue),
        )
        val underTest = ArrayPutExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT extension type type`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayPutExpression(array, value)

        val actual = arrayPut(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PUT extension type string`() {
        val array = someStringArrayField()
        val value = someString()
        val expected = ArrayPutExpression(array, value.toDopeType())

        val actual = arrayPut(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PUT extension type number`() {
        val array = someNumberArrayField()
        val value = someNumber()
        val expected = ArrayPutExpression(array, value.toDopeType())

        val actual = arrayPut(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_PUT extension type boolean`() {
        val array = someBooleanArrayField()
        val value = someBoolean()
        val expected = ArrayPutExpression(array, value.toDopeType())

        val actual = arrayPut(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
