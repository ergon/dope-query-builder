package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isLessThan
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LessThanTest {
    @Test
    fun `should support less than with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = LessThanExpression(left.toDopeType(), right)

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with NumberType CMFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = LessThanExpression(left, right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = LessThanExpression(left.toDopeType(), right)

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with StringType CMFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = LessThanExpression(left, right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = LessThanExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = LessThanExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = LessThanExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = LessThanExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
