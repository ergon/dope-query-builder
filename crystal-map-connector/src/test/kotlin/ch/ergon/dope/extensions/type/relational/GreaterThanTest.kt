package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isGreaterThan
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterThanTest {
    @Test
    fun `should support greater than with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with NumberType CMFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with StringType CMFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
