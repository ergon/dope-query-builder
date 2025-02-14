package ch.ergon.dope.extensions.expression.single.type.function.array

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.function.array.arrayConcat
import ch.ergon.dope.extension.expression.single.type.function.array.arrayContains
import ch.ergon.dope.extension.expression.single.type.function.array.arrayDistinct
import ch.ergon.dope.extension.expression.single.type.function.array.arrayIntersect
import ch.ergon.dope.extension.expression.single.type.function.array.arrayLength
import ch.ergon.dope.extension.expression.single.type.function.array.arraySum
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMObjectList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArraySumExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_CONCAT with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayConcat(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayConcat(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayConcat(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT with CM Object list`() {
        val firstList = someCMObjectList("first")
        val secondList = someCMObjectList("second")
        val expected = ArrayConcatExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayConcat(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayContains(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayContains(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONTAINS with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanField("second")
        val expected = ArrayContainsExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayContains(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = arrayDistinct(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM String list`() {
        val cMJsonList = someCMStringList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = arrayDistinct(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Boolean list`() {
        val cMJsonList = someCMBooleanList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = arrayDistinct(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Object list`() {
        val cMJsonList = someCMObjectList()
        val expected = ArrayDistinctExpression(cMJsonList.toDopeType())

        val actual = arrayDistinct(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Number list`() {
        val firstList = someCMNumberList("first")
        val secondList = someCMNumberList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayIntersect(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM String list`() {
        val firstList = someCMStringList("first")
        val secondList = someCMStringList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayIntersect(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_INTERSECT with CM Boolean list`() {
        val firstList = someCMBooleanList("first")
        val secondList = someCMBooleanList("second")
        val expected = ArrayIntersectExpression(firstList.toDopeType(), secondList.toDopeType())

        val actual = arrayIntersect(firstList, secondList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = arrayLength(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM String list`() {
        val cMJsonList = someCMStringList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = arrayLength(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Boolean list`() {
        val cMJsonList = someCMBooleanList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = arrayLength(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Object list`() {
        val cMJsonList = someCMObjectList()
        val expected = ArrayLengthExpression(cMJsonList.toDopeType())

        val actual = arrayLength(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SUM with CM Number list`() {
        val cMJsonList = someCMNumberList()
        val expected = ArraySumExpression(cMJsonList.toDopeType())

        val actual = arraySum(cMJsonList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
