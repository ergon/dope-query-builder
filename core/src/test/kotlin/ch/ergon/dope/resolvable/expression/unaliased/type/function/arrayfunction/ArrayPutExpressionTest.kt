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
        )
        val underTest = ArrayPutExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_PUT($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPutExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PUT(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayPutExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_PUT($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
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