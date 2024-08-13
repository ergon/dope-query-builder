package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isLessOrEqualThan
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LessOrEqualThanTest {
    @Test
    fun `should support less or equal than with CMJsonFieldNumber CMJsonFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMJsonFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with NumberType CMJsonFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with Number CMJsonFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMJsonFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMJsonFieldString CMJsonFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMJsonFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with StringType CMJsonFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with String CMJsonFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMJsonFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = LessOrEqualThanExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less or equal than with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = LessOrEqualThanExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
