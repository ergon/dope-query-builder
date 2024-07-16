package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.inArray
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanFieldList
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMConverterBooleanList
import ch.ergon.dope.helper.someCMConverterNumberList
import ch.ergon.dope.helper.someCMConverterStringList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberFieldList
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringFieldList
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InTest {
    @Test
    fun `should support in array with CMFieldNumber ListNumber`() {
        val left = someCMNumberField()
        val right = someNumberFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldString ListString`() {
        val left = someCMStringField()
        val right = someStringFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldBoolean ListBoolean`() {
        val left = someCMBooleanField()
        val right = someBooleanFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldNumber CMListNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldString CMListString`() {
        val left = someCMStringField()
        val right = someCMStringList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldBoolean CMListBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldNumber Collection`() {
        val left = someCMNumberField()
        val right = listOf(someNumberField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldString Collection`() {
        val left = someCMStringField()
        val right = listOf(someStringField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with CMFieldBoolean Collection`() {
        val left = someCMBooleanField()
        val right = listOf(someBooleanField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with Number CMList`() {
        val left = someNumber()
        val right = someCMNumberList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with String CMList`() {
        val left = someString()
        val right = someCMStringList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with Boolean CMList`() {
        val left = someBoolean()
        val right = someCMBooleanList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberList()
        val expected = InExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringList()
        val expected = InExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in array with date CMConverterBooleanField`() {
        val left = someDate()
        val right = someCMConverterBooleanList()
        val expected = InExpression(true.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
