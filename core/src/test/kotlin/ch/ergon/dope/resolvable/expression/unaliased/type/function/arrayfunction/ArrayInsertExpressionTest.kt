package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanSelectRawClause
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayInsertExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_INSERT`() {
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(`numberArrayField`, 1, `numberField`)",
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT($1, 1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(\$$parameterName, 1, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(parameterName), 1.toDopeType(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with positional second parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(`numberArrayField`, $1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with named second parameter`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(`numberArrayField`, \$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with positional third parameter`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(`numberArrayField`, 1, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with named third parameter`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(`numberArrayField`, 1, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayInsertExpression(someNumberArrayField(), 1.toDopeType(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with positional all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT($1, $2, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayInsertExpression(parameterValue.asParameter(), parameterValue2.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT with named all parameters`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterValue2 = 1
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_INSERT(\$$parameterName, \$$parameterName2, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ArrayInsertExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            someNumberField(),
        )

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

    @Test
    fun `should support ARRAY_INSERT extension select type type`() {
        val array = someNumberSelectRawClause()
        val position = someNumberField()
        val value = someNumberField()
        val expected = ArrayInsertExpression(array.asExpression(), position, value)

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select type string`() {
        val array = someStringSelectRawClause()
        val position = someNumberField()
        val value = someString()
        val expected = ArrayInsertExpression(array.asExpression(), position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select type number`() {
        val array = someNumberSelectRawClause()
        val position = someNumberField()
        val value = someNumber()
        val expected = ArrayInsertExpression(array.asExpression(), position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select type boolean`() {
        val array = someBooleanSelectRawClause()
        val position = someNumberField()
        val value = someBoolean()
        val expected = ArrayInsertExpression(array.asExpression(), position, value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select number type`() {
        val array = someNumberSelectRawClause()
        val position = someNumber()
        val value = someNumberField()
        val expected = ArrayInsertExpression(array.asExpression(), position.toDopeType(), value)

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select number string`() {
        val array = someStringSelectRawClause()
        val position = someNumber()
        val value = someString()
        val expected = ArrayInsertExpression(array.asExpression(), position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select number number`() {
        val array = someNumberSelectRawClause()
        val position = someNumber()
        val value = someNumber()
        val expected = ArrayInsertExpression(array.asExpression(), position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INSERT extension select number boolean`() {
        val array = someBooleanSelectRawClause()
        val position = someNumber()
        val value = someBoolean()
        val expected = ArrayInsertExpression(array.asExpression(), position.toDopeType(), value.toDopeType())

        val actual = arrayInsert(array, position, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
