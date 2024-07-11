package ch.ergon.dope.extensions.type.array

import ch.ergon.dope.extension.type.array.arrayConcat
import ch.ergon.dope.extension.type.array.arrayContains
import ch.ergon.dope.extension.type.array.arrayDistinct
import ch.ergon.dope.extension.type.array.arrayIntersect
import ch.ergon.dope.extension.type.array.arrayLength
import ch.ergon.dope.extension.type.array.arraySum
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayFunctionTest {
    @Test
    fun `should support ARRAY_CONCAT with CM Number list`() {
        val expected = "ARRAY_CONCAT(`someNumberList`, `anotherNumberList`)"

        val actual = arrayConcat(someCMNumberList(), someCMNumberList("anotherNumberList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with CM String list`() {
        val expected = "ARRAY_CONCAT(`someStringList`, `anotherStringList`)"

        val actual = arrayConcat(someCMStringList(), someCMStringList("anotherStringList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with CM Boolean list`() {
        val expected = "ARRAY_CONCAT(`someBooleanList`, `anotherBooleanList`)"

        val actual = arrayConcat(someCMBooleanList(), someCMBooleanList("anotherBooleanList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Number list`() {
        val expected = "ARRAY_CONTAINS(`someNumberList`, `someNumberField`)"

        val actual = arrayContains(someCMNumberList(), someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM String list`() {
        val expected = "ARRAY_CONTAINS(`someStringList`, `someStringField`)"

        val actual = arrayContains(someCMStringList(), someCMStringField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Boolean list`() {
        val expected = "ARRAY_CONTAINS(`someBooleanList`, `someBooleanField`)"

        val actual = arrayContains(someCMBooleanList(), someCMBooleanField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Number list`() {
        val expected = "ARRAY_DISTINCT(`someNumberList`)"

        val actual = arrayDistinct(someCMNumberList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM String list`() {
        val expected = "ARRAY_DISTINCT(`someStringList`)"

        val actual = arrayDistinct(someCMStringList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Boolean list`() {
        val expected = "ARRAY_DISTINCT(`someBooleanList`)"

        val actual = arrayDistinct(someCMBooleanList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Number list`() {
        val expected = "ARRAY_INTERSECT(`someNumberList`, `anotherNumberList`)"

        val actual = arrayIntersect(someCMNumberList(), someCMNumberList("anotherNumberList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM String list`() {
        val expected = "ARRAY_INTERSECT(`someStringList`, `anotherStringList`)"

        val actual = arrayIntersect(someCMStringList(), someCMStringList("anotherStringList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Boolean list`() {
        val expected = "ARRAY_INTERSECT(`someBooleanList`, `anotherBooleanList`)"

        val actual = arrayIntersect(someCMBooleanList(), someCMBooleanList("anotherBooleanList")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Number list`() {
        val expected = "ARRAY_LENGTH(`someNumberList`)"

        val actual = arrayLength(someCMNumberList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM String list`() {
        val expected = "ARRAY_LENGTH(`someStringList`)"

        val actual = arrayLength(someCMStringList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Boolean list`() {
        val expected = "ARRAY_LENGTH(`someBooleanList`)"

        val actual = arrayLength(someCMBooleanList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with CM Number list`() {
        val expected = "ARRAY_SUM(`someNumberList`)"

        val actual = arraySum(someCMNumberList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with CM String list`() {
        val expected = "ARRAY_SUM(`someStringList`)"

        val actual = arraySum(someCMStringList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with CM Boolean list`() {
        val expected = "ARRAY_SUM(`someBooleanList`)"

        val actual = arraySum(someCMBooleanList()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }
}
