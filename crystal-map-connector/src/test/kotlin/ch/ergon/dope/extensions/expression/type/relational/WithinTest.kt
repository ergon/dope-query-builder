package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.notWithinArray
import ch.ergon.dope.extension.expression.type.relational.withinArray
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
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberFieldList
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringFieldList
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.expression.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.type.collection.WithinExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WithinTest {
    @Test
    fun `should support within array with CMJsonField and CMJsonList`() {
        val field = someCMNumberField()
        val list = someCMNumberList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and CMJsonList string`() {
        val field = someCMStringField()
        val list = someCMStringList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and CMJsonList boolean`() {
        val field = someCMBooleanField()
        val list = someCMBooleanList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and select clause`() {
        val field = someCMNumberField()
        val list = SelectRawClause(someNumberField())
        val expected = WithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and select clause string`() {
        val field = someCMStringField()
        val list = SelectRawClause(someStringField())
        val expected = WithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and select clause boolean`() {
        val field = someCMBooleanField()
        val list = SelectRawClause(someBooleanField())
        val expected = WithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and collection`() {
        val field = someCMStringField()
        val list = listOf("a".toDopeType(), "b".toDopeType())
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and collection number types`() {
        val field = someCMNumberField()
        val list = listOf(someNumberField(), someNumberField("other"))
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and collection string types`() {
        val field = someCMStringField()
        val list = listOf(someStringField(), someStringField("other"))
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and collection boolean types`() {
        val field = someCMBooleanField()
        val list = listOf(someBooleanField(), someBooleanField("other"))
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and type array`() {
        val field = someCMNumberField()
        val list = someNumberFieldList()
        val expected = WithinExpression(field.toDopeType(), list)

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and type array string`() {
        val field = someCMStringField()
        val list = someStringFieldList()
        val expected = WithinExpression(field.toDopeType(), list)

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with CMJsonField and type array boolean`() {
        val field = someCMBooleanField()
        val list = someBooleanFieldList()
        val expected = WithinExpression(field.toDopeType(), list)

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with TypeExpression and CMJsonList`() {
        val field = someNumberField()
        val list = someCMNumberList()
        val expected = WithinExpression(field, list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with TypeExpression string and CMJsonList`() {
        val field = someStringField()
        val list = someCMStringList()
        val expected = WithinExpression(field, list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with TypeExpression boolean and CMJsonList`() {
        val field = someBooleanField()
        val list = someCMBooleanList()
        val expected = WithinExpression(field, list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with primitive and CMJsonList`() {
        val field = 5
        val list = someCMNumberList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with primitive string and CMJsonList`() {
        val field = someString()
        val list = someCMStringList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with primitive boolean and CMJsonList`() {
        val field = someBoolean()
        val list = someCMBooleanList()
        val expected = WithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with converter list`() {
        val field = someDate()
        val list = someCMConverterNumberList()
        val expected = WithinExpression(field.toInstant().toEpochMilli().toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with converter list string`() {
        val field = someDate()
        val list = someCMConverterStringList()
        val expected = WithinExpression(field.toInstant().toEpochMilli().toString().toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support within array with converter list boolean`() {
        val field = someDate()
        val list = someCMConverterBooleanList()
        val expected = WithinExpression(true.toDopeType(), list.toDopeType())

        val actual = field.withinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and CMJsonList`() {
        val field = someCMNumberField()
        val list = someCMNumberList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and CMJsonList string`() {
        val field = someCMStringField()
        val list = someCMStringList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and CMJsonList boolean`() {
        val field = someCMBooleanField()
        val list = someCMBooleanList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and select clause`() {
        val field = someCMNumberField()
        val list = SelectRawClause(someNumberField())
        val expected = NotWithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and select clause string`() {
        val field = someCMStringField()
        val list = SelectRawClause(someStringField())
        val expected = NotWithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and select clause boolean`() {
        val field = someCMBooleanField()
        val list = SelectRawClause(someBooleanField())
        val expected = NotWithinExpression(field.toDopeType(), list.asExpression())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and collection`() {
        val field = someCMStringField()
        val list = listOf("a".toDopeType(), "b".toDopeType())
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and collection number types`() {
        val field = someCMNumberField()
        val list = listOf(someNumberField(), someNumberField("other"))
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and collection string types`() {
        val field = someCMStringField()
        val list = listOf(someStringField(), someStringField("other"))
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and collection boolean types`() {
        val field = someCMBooleanField()
        val list = listOf(someBooleanField(), someBooleanField("other"))
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and type array`() {
        val field = someCMNumberField()
        val list = someNumberFieldList()
        val expected = NotWithinExpression(field.toDopeType(), list)

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and type array string`() {
        val field = someCMStringField()
        val list = someStringFieldList()
        val expected = NotWithinExpression(field.toDopeType(), list)

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with CMJsonField and type array boolean`() {
        val field = someCMBooleanField()
        val list = someBooleanFieldList()
        val expected = NotWithinExpression(field.toDopeType(), list)

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with TypeExpression and CMJsonList`() {
        val field = someNumberField()
        val list = someCMNumberList()
        val expected = NotWithinExpression(field, list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with TypeExpression string and CMJsonList`() {
        val field = someStringField()
        val list = someCMStringList()
        val expected = NotWithinExpression(field, list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with TypeExpression boolean and CMJsonList`() {
        val field = someBooleanField()
        val list = someCMBooleanList()
        val expected = NotWithinExpression(field, list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with primitive and CMJsonList`() {
        val field = 5
        val list = someCMNumberList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with primitive string and CMJsonList`() {
        val field = someString()
        val list = someCMStringList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not within array with primitive boolean and CMJsonList`() {
        val field = someBoolean()
        val list = someCMBooleanList()
        val expected = NotWithinExpression(field.toDopeType(), list.toDopeType())

        val actual = field.notWithinArray(list)

        assertEquals(expected, actual)
    }
}
