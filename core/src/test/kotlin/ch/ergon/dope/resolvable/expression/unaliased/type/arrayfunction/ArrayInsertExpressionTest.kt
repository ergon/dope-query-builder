package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

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

class ArrayInsertExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_INSERT`() {
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, 1, `numberField`)",
            emptyMap(),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_INSERT($1, 1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, $1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with third parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            "ARRAY_INSERT(`numberArrayField`, 1, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with first and second parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_INSERT($1, $2, `numberField`)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), parameterValue2.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with first and third parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            "ARRAY_INSERT($1, 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), 1.toDopeType(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterValue3 = 2
        val expected = DopeQuery(
            "ARRAY_INSERT($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT extension type type type`() {
        val array = someNumberArrayField()
        val position = someNumberField()
        val value = someNumberField()
        val expected = ArrayInsertExpression(array, position, value)

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type type string`() {
        val array = someStringArrayField()
        val position = someNumberField()
        val value = someString()
        val expected = ArrayInsertExpression(array, position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type type number`() {
        val array = someNumberArrayField()
        val position = someNumberField()
        val value = someNumber()
        val expected = ArrayInsertExpression(array, position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type type boolean`() {
        val array = someBooleanArrayField()
        val position = someNumberField()
        val value = someBoolean()
        val expected = ArrayInsertExpression(array, position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type number type`() {
        val array = someNumberArrayField()
        val position = someNumber()
        val value = someNumberField()
        val expected = ArrayInsertExpression(array, position.toDopeType(), value)

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type number string`() {
        val array = someStringArrayField()
        val position = someNumber()
        val value = someString()
        val expected = ArrayInsertExpression(array, position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type number number`() {
        val array = someNumberArrayField()
        val position = someNumber()
        val value = someNumber()
        val expected = ArrayInsertExpression(array, position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension type number boolean`() {
        val array = someBooleanArrayField()
        val position = someNumber()
        val value = someBoolean()
        val expected = ArrayInsertExpression(array, position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
