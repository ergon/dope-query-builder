package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.inArray
import ch.ergon.dope.extension.expression.type.relational.notInArray
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanFieldList
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterBooleanList
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterNumberList
import ch.ergon.dope.helper.someCMConverterStringField
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
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.expression.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.type.collection.NotInExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InTest {
    @Test
    fun `should support in array with CMJsonFieldNumber ListNumber`() {
        val left = someCMNumberField()
        val right = someNumberFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldString ListString`() {
        val left = someCMStringField()
        val right = someStringFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldBoolean ListBoolean`() {
        val left = someCMBooleanField()
        val right = someBooleanFieldList()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldNumber CMJsonListNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldString CMJsonListString`() {
        val left = someCMStringField()
        val right = someCMStringList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldBoolean CMJsonListBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldNumber Collection`() {
        val left = someCMNumberField()
        val right = listOf(someNumberField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldNumber Collection of numbers`() {
        val left = someCMNumberField()
        val right = listOf(1, 2, 3)
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldString Collection`() {
        val left = someCMStringField()
        val right = listOf(someStringField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldBoolean Collection`() {
        val left = someCMBooleanField()
        val right = listOf(someBooleanField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldString Collection of strings`() {
        val left = someCMStringField()
        val right = listOf("a", "b")
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonFieldBoolean Collection of booleans`() {
        val left = someCMBooleanField()
        val right = listOf(true, false)
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with Number CMJsonList`() {
        val left = someNumber()
        val right = someCMNumberList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with String CMJsonList`() {
        val left = someString()
        val right = someCMStringList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with Boolean CMJsonList`() {
        val left = someBoolean()
        val right = someCMBooleanList()
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with TypeExpression and CMJsonList`() {
        val left = someNumberField()
        val right = someCMNumberList()
        val expected = InExpression(left, right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with TypeExpression string and CMJsonList`() {
        val left = someStringField()
        val right = someCMStringList()
        val expected = InExpression(left, right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with TypeExpression boolean and CMJsonList`() {
        val left = someBooleanField()
        val right = someCMBooleanList()
        val expected = InExpression(left, right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonField and select clause`() {
        val left = someCMNumberField()
        val right = SelectRawClause(someNumberField())
        val expected = InExpression(left.toDopeType(), right.asExpression())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonField string and select clause`() {
        val left = someCMStringField()
        val right = SelectRawClause(someStringField())
        val expected = InExpression(left.toDopeType(), right.asExpression())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMJsonField boolean and select clause`() {
        val left = someCMBooleanField()
        val right = SelectRawClause(someBooleanField())
        val expected = InExpression(left.toDopeType(), right.asExpression())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberList()
        val expected = InExpression(left.toInstant().toEpochMilli().toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringList()
        val expected = InExpression(left.toInstant().toEpochMilli().toString().toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with date CMConverterBooleanField`() {
        val left = someDate()
        val right = someCMConverterBooleanList()
        val expected = InExpression(true.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMConverterNumberField and date collection`() {
        val left = someCMConverterNumberField()
        val dates = listOf(someDate(), someDate())
        val expected = InExpression(left.toDopeType(), dates.map { it.toDopeType(left) }.toDopeType())

        val actual = left.inArray(dates)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMConverterStringField and date collection`() {
        val left = someCMConverterStringField()
        val dates = listOf(someDate(), someDate())
        val expected = InExpression(left.toDopeType(), dates.map { it.toDopeType(left) }.toDopeType())

        val actual = left.inArray(dates)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in array with CMConverterBooleanField and date collection`() {
        val left = someCMConverterBooleanField()
        val dates = listOf(someDate(), someDate())
        val expected = InExpression(left.toDopeType(), dates.map { it.toDopeType(left) }.toDopeType())

        val actual = left.inArray(dates)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldNumber ListNumber`() {
        val left = someCMNumberField()
        val right = someNumberFieldList()
        val expected = NotInExpression(left.toDopeType(), right)

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldString ListString`() {
        val left = someCMStringField()
        val right = someStringFieldList()
        val expected = NotInExpression(left.toDopeType(), right)

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldBoolean ListBoolean`() {
        val left = someCMBooleanField()
        val right = someBooleanFieldList()
        val expected = NotInExpression(left.toDopeType(), right)

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonField and select clause`() {
        val left = someCMNumberField()
        val right = SelectRawClause(someNumberField())
        val expected = NotInExpression(left.toDopeType(), right.asExpression())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonField string and select clause`() {
        val left = someCMStringField()
        val right = SelectRawClause(someStringField())
        val expected = NotInExpression(left.toDopeType(), right.asExpression())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonField boolean and select clause`() {
        val left = someCMBooleanField()
        val right = SelectRawClause(someBooleanField())
        val expected = NotInExpression(left.toDopeType(), right.asExpression())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldNumber CMJsonListNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldString CMJsonListString`() {
        val left = someCMStringField()
        val right = someCMStringList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldBoolean CMJsonListBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldNumber Collection`() {
        val left = someCMNumberField()
        val right = listOf(someNumberField())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldNumber Collection of numbers`() {
        val left = someCMNumberField()
        val right = listOf(1, 2, 3)
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldString Collection of strings`() {
        val left = someCMStringField()
        val right = listOf("a", "b")
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldBoolean Collection of booleans`() {
        val left = someCMBooleanField()
        val right = listOf(true, false)
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldString Collection`() {
        val left = someCMStringField()
        val right = listOf(someStringField())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with CMJsonFieldBoolean Collection`() {
        val left = someCMBooleanField()
        val right = listOf(someBooleanField())
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with Number CMJsonList`() {
        val left = someNumber()
        val right = someCMNumberList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with String CMJsonList`() {
        val left = someString()
        val right = someCMStringList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with Boolean CMJsonList`() {
        val left = someBoolean()
        val right = someCMBooleanList()
        val expected = NotInExpression(left.toDopeType(), right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with TypeExpression and CMJsonList`() {
        val left = someNumberField()
        val right = someCMNumberList()
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with TypeExpression string and CMJsonList`() {
        val left = someStringField()
        val right = someCMStringList()
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not in array with TypeExpression boolean and CMJsonList`() {
        val left = someBooleanField()
        val right = someCMBooleanList()
        val expected = NotInExpression(left, right.toDopeType())

        val actual = left.notInArray(right)

        assertEquals(expected, actual)
    }
}
