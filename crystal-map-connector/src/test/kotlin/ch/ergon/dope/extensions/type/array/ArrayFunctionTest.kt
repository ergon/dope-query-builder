package ch.ergon.dope.extensions.type.array

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.array.arrayConcat
import ch.ergon.dope.extension.type.array.arrayContains
import ch.ergon.dope.extension.type.array.arrayDistinct
import ch.ergon.dope.extension.type.array.arrayIntersect
import ch.ergon.dope.extension.type.array.arrayLength
import ch.ergon.dope.extension.type.array.arraySum
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.ArraySumExpression
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
        val cmList = someCMNumberList()
        val expected = ArrayDistinctExpression(cmList.toDopeType())

        val actual = arrayDistinct(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM String list`() {
        val cmList = someCMStringList()
        val expected = ArrayDistinctExpression(cmList.toDopeType())

        val actual = arrayDistinct(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_DISTINCT with CM Boolean list`() {
        val cmList = someCMBooleanList()
        val expected = ArrayDistinctExpression(cmList.toDopeType())

        val actual = arrayDistinct(cmList)

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
        val cmList = someCMNumberList()
        val expected = ArrayLengthExpression(cmList.toDopeType())

        val actual = arrayLength(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM String list`() {
        val cmList = someCMStringList()
        val expected = ArrayLengthExpression(cmList.toDopeType())

        val actual = arrayLength(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_LENGTH with CM Boolean list`() {
        val cmList = someCMBooleanList()
        val expected = ArrayLengthExpression(cmList.toDopeType())

        val actual = arrayLength(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SUM with CM Number list`() {
        val cmList = someCMNumberList()
        val expected = ArraySumExpression(cmList.toDopeType())

        val actual = arraySum(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SUM with CM String list`() {
        val cmList = someCMStringList()
        val expected = ArraySumExpression(cmList.toDopeType())

        val actual = arraySum(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SUM with CM Boolean list`() {
        val cmList = someCMBooleanList()
        val expected = ArraySumExpression(cmList.toDopeType())

        val actual = arraySum(cmList)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
