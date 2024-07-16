package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isNotEqualTo
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotEqualsTest {
    @Test
    fun `should support not equals to with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with NumberType CMFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with StringType CMFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldBoolean CMFieldBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldBoolean BooleanType`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with BooleanType CMFieldBoolean`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with Boolean CMFieldBoolean`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMFieldBoolean Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = NotEqualsExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = NotEqualsExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with CMConverterBooleanField date`() {
        val left = someCMConverterBooleanField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), true.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals to with date CMConverterBooleanField`() {
        val left = someDate()
        val right = someCMConverterBooleanField()
        val expected = NotEqualsExpression(true.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
