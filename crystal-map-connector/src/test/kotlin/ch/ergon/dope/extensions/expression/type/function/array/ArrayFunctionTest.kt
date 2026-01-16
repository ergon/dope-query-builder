package ch.ergon.dope.extensions.expression.type.function.array

import ch.ergon.dope.extension.expression.type.function.array.concat
import ch.ergon.dope.extension.expression.type.function.array.contains
import ch.ergon.dope.extension.expression.type.function.array.distinct
import ch.ergon.dope.extension.expression.type.function.array.intersect
import ch.ergon.dope.extension.expression.type.function.array.length
import ch.ergon.dope.extension.expression.type.function.array.sum
import ch.ergon.dope.extension.expression.type.function.array.unpack
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySumExpression
import ch.ergon.dope.resolvable.expression.type.function.array.UnpackExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionTest {
    @Test
    fun `should support ARRAY_CONCAT with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.concat(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.concat(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.concat(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with CM Object list`() {
        val firstList = someCMObjectList("first")
        val secondList = someCMObjectList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.concat(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.contains(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.contains(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.contains(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.distinct()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM String list`() {
        val cMJsonList = someCMStringList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.distinct()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Boolean list`() {
        val cMJsonList = someCMBooleanList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.distinct()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Object list`() {
        val cMJsonList = someCMObjectList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.distinct()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.intersect(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.intersect(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = firstList.intersect(secondList)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.length()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM String list`() {
        val cMJsonList = someCMStringList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.length()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Boolean list`() {
        val cMJsonList = someCMBooleanList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.length()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Object list`() {
        val cMJsonList = someCMObjectList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.length()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support get asterisk with CM Object list as receiver`() {
        val cMJsonList = someCMObjectList()
        val expected = UnpackExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.unpack()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArraySumExpression(cMJsonList.toDopeType())

        val actual = cMJsonList.sum()

        assertEquals(expected, actual)
    }
}
