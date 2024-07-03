package ch.ergon.dope

import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayAppend
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayAverage
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayConcat
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayCount
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayDistinct
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayExcept
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayFlatten
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayIfNull
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayInsert
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayIntersect
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayLength
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayMax
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayMin
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayMove
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayPosition
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayPrepend
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayPut
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayRemove
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayReplace
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayReverse
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arraySort
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arraySum
import ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction.arrayUnion
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayFunctionsTest {
    @Test
    fun `should support ARRAY_APPEND`() {
        val expected = "ARRAY_APPEND(`numberArrayField`, 1, 2, 3)"

        val actual = arrayAppend(
            someNumberArrayField(),
            1.toDopeType(),
            2.toDopeType(),
            3.toDopeType(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_AVG`() {
        val expected = "ARRAY_AVG(`numberArrayField`)"

        val actual = arrayAverage(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with two arrays`() {
        val expected = "ARRAY_CONCAT(`firstNumberArrayField`, `secondNumberArrayField`)"

        val actual = arrayConcat(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with four arrays`() {
        val expected = "ARRAY_CONCAT(`firstNumberArrayField`, `secondNumberArrayField`, `thirdNumberArrayField`, `fourthNumberArrayField`)"

        val actual = arrayConcat(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
            someNumberArrayField("thirdNumberArrayField"),
            someNumberArrayField("fourthNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with Strings`() {
        val expected = "ARRAY_CONTAINS(`stringArrayField`, \"test\")"

        val actual = arrayContains(
            someStringArrayField(),
            "test",
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with Numbers`() {
        val expected = "ARRAY_CONTAINS(`numberArrayField`, 1)"

        val actual = arrayContains(
            someNumberArrayField(),
            1,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONTAINS with Booleans`() {
        val expected = "ARRAY_CONTAINS(`booleanArrayField`, TRUE)"

        val actual = arrayContains(
            someBooleanArrayField(),
            true,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT`() {
        val expected = "ARRAY_COUNT(`numberArrayField`)"

        val actual = arrayCount(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT`() {
        val expected = "ARRAY_DISTINCT(`numberArrayField`)"

        val actual = arrayDistinct(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT`() {
        val expected = "ARRAY_EXCEPT(`firstNumberArrayField`, `secondNumberArrayField`)"

        val actual = arrayExcept(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN`() {
        val expected = "ARRAY_FLATTEN(`numberArrayField`, 1)"

        val actual = arrayFlatten(
            someNumberArrayField(),
            1,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL`() {
        val expected = "ARRAY_IFNULL(`numberArrayField`)"

        val actual = arrayIfNull(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INSERT`() {
        val expected = "ARRAY_INSERT(`numberArrayField`, 1, `numberField`, `anotherNumberField`)"

        val actual = arrayInsert(
            someNumberArrayField(),
            1,
            someNumberField(),
            someNumberField("anotherNumberField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with two arrays`() {
        val expected = "ARRAY_INTERSECT(`firstNumberArrayField`, `secondNumberArrayField`)"

        val actual = arrayIntersect(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_INTERSECT with four arrays`() {
        val expected = "ARRAY_INTERSECT(`firstNumberArrayField`, `secondNumberArrayField`, `thirdNumberArrayField`, `fourthNumberArrayField`)"

        val actual = arrayIntersect(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
            someNumberArrayField("thirdNumberArrayField"),
            someNumberArrayField("fourthNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_LENGTH`() {
        val expected = "ARRAY_LENGTH(`numberArrayField`)"

        val actual = arrayLength(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX`() {
        val expected = "ARRAY_MAX(`numberArrayField`)"

        val actual = arrayMax(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MIN`() {
        val expected = "ARRAY_MIN(`numberArrayField`)"

        val actual = arrayMin(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MOVE`() {
        val expected = "ARRAY_MOVE(`numberArrayField`, 1, 2)"

        val actual = arrayMove(
            someNumberArrayField(),
            1,
            2,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION`() {
        val expected = "ARRAY_POSITION(`numberArrayField`, 1)"

        val actual = arrayPosition(
            someNumberArrayField(),
            1,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PREPEND`() {
        val expected = "ARRAY_PREPEND(1, 2, 3, `numberArrayField`)"

        val actual = arrayPrepend(
            someNumberArrayField(),
            1.toDopeType(),
            2.toDopeType(),
            3.toDopeType(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_PUT`() {
        val expected = "ARRAY_PUT(`numberArrayField`, 1, 2, 3)"

        val actual = arrayPut(
            someNumberArrayField(),
            1.toDopeType(),
            2.toDopeType(),
            3.toDopeType(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REMOVE`() {
        val expected = "ARRAY_REMOVE(`numberArrayField`, 1, 2, 3)"

        val actual = arrayRemove(
            someNumberArrayField(),
            1.toDopeType(),
            2.toDopeType(),
            3.toDopeType(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPLACE`() {
        val expected = "ARRAY_REPLACE(`numberArrayField`, 1, 2, 1)"

        val actual = arrayReplace(
            someNumberArrayField(),
            1.toDopeType(),
            2.toDopeType(),
            1,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REVERSE`() {
        val expected = "ARRAY_REVERSE(`numberArrayField`)"

        val actual = arrayReverse(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SORT`() {
        val expected = "ARRAY_SORT(`numberArrayField`)"

        val actual = arraySort(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM`() {
        val expected = "ARRAY_SUM(`numberArrayField`)"

        val actual = arraySum(
            someNumberArrayField(),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with two arrays`() {
        val expected = "ARRAY_UNION(`firstNumberArrayField`, `secondNumberArrayField`)"

        val actual = arrayUnion(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with four arrays`() {
        val expected = "ARRAY_UNION(`firstNumberArrayField`, `secondNumberArrayField`, `thirdNumberArrayField`, `fourthNumberArrayField`)"

        val actual = arrayUnion(
            someNumberArrayField("firstNumberArrayField"),
            someNumberArrayField("secondNumberArrayField"),
            someNumberArrayField("thirdNumberArrayField"),
            someNumberArrayField("fourthNumberArrayField"),
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }
}
