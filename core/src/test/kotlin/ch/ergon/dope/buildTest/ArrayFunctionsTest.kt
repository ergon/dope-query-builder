package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someObjectSelectRawClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.function.array.arrayAppend
import ch.ergon.dope.resolvable.expression.type.function.array.arrayAverage
import ch.ergon.dope.resolvable.expression.type.function.array.arrayBinarySearch
import ch.ergon.dope.resolvable.expression.type.function.array.arrayConcat
import ch.ergon.dope.resolvable.expression.type.function.array.arrayContains
import ch.ergon.dope.resolvable.expression.type.function.array.arrayCount
import ch.ergon.dope.resolvable.expression.type.function.array.arrayDistinct
import ch.ergon.dope.resolvable.expression.type.function.array.arrayExcept
import ch.ergon.dope.resolvable.expression.type.function.array.arrayFlatten
import ch.ergon.dope.resolvable.expression.type.function.array.arrayIfNull
import ch.ergon.dope.resolvable.expression.type.function.array.arrayInsert
import ch.ergon.dope.resolvable.expression.type.function.array.arrayIntersect
import ch.ergon.dope.resolvable.expression.type.function.array.arrayLength
import ch.ergon.dope.resolvable.expression.type.function.array.arrayMax
import ch.ergon.dope.resolvable.expression.type.function.array.arrayMin
import ch.ergon.dope.resolvable.expression.type.function.array.arrayMove
import ch.ergon.dope.resolvable.expression.type.function.array.arrayPosition
import ch.ergon.dope.resolvable.expression.type.function.array.arrayPrepend
import ch.ergon.dope.resolvable.expression.type.function.array.arrayPut
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRange
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRemove
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.type.function.array.arrayReplace
import ch.ergon.dope.resolvable.expression.type.function.array.arrayReverse
import ch.ergon.dope.resolvable.expression.type.function.array.arraySort
import ch.ergon.dope.resolvable.expression.type.function.array.arraySum
import ch.ergon.dope.resolvable.expression.type.function.array.arraySymDiff
import ch.ergon.dope.resolvable.expression.type.function.array.arraySymDiffN
import ch.ergon.dope.resolvable.expression.type.function.array.arrayUnion
import ch.ergon.dope.resolvable.expression.type.function.array.unpack
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.getNumberArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsTest {
    @Test
    fun `should support array append with array access`() {
        val expected = "SELECT ARRAY_APPEND(`stringArrayField`, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayAppend(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array average with addition`() {
        val expected = "SELECT (ARRAY_AVG(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                arrayAverage(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array binary search with addition`() {
        val expected = "SELECT (ARRAY_BINARY_SEARCH(`numberArrayField`, `numberField`) + 1)"

        val actual = QueryBuilder
            .select(
                arrayBinarySearch(someNumberArrayField(), someNumberField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array concat with array access`() {
        val expected = "SELECT ARRAY_CONCAT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayConcat(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array contains with boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ARRAY_CONTAINS(`stringArrayField`, `stringField`)"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .where(
                arrayContains(someStringArrayField(), someStringField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array count with addition`() {
        val expected = "SELECT (ARRAY_COUNT(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                arrayCount(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array distinct with array access`() {
        val expected = "SELECT ARRAY_DISTINCT(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayDistinct(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array except with array access`() {
        val expected = "SELECT ARRAY_EXCEPT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayExcept(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array flatten with array access`() {
        val expected = "SELECT ARRAY_FLATTEN(`stringArrayField`, 2)[0]"

        val actual = QueryBuilder
            .select(
                arrayFlatten(someStringArrayField(), 2).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array if null with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_IFNULL(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    arrayIfNull(someStringArrayField()),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array insert with array access`() {
        val expected = "SELECT ARRAY_INSERT(`stringArrayField`, 0, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayInsert(someStringArrayField(), 0, someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array intersect with array access`() {
        val expected = "SELECT ARRAY_INTERSECT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayIntersect(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array length with addition`() {
        val expected = "SELECT (ARRAY_LENGTH(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                arrayLength(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array max with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_MAX(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    arrayMax(someStringArrayField()),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array min with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_MIN(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    arrayMin(someStringArrayField()),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array move with array access`() {
        val expected = "SELECT ARRAY_MOVE(`stringArrayField`, 0, 1)[0]"

        val actual = QueryBuilder
            .select(
                arrayMove(someStringArrayField(), 0, 1).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array position with addition`() {
        val expected = "SELECT (ARRAY_POSITION(`stringArrayField`, `stringField`) + 1)"

        val actual = QueryBuilder
            .select(
                arrayPosition(someStringArrayField(), someStringField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array prepend with array access`() {
        val expected = "SELECT ARRAY_PREPEND(`stringField`, `stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayPrepend(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array put with array access`() {
        val expected = "SELECT ARRAY_PUT(`stringArrayField`, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayPut(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range with array access`() {
        val expected = "SELECT ARRAY_RANGE(0, 10, 2)[0]"

        val actual = QueryBuilder
            .select(
                arrayRange(0, 10, 2).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array remove with array access`() {
        val expected = "SELECT ARRAY_REMOVE(`stringArrayField`, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayRemove(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array repeat with array access`() {
        val expected = "SELECT ARRAY_REPEAT(`stringField`, 5)[0]"

        val actual = QueryBuilder
            .select(
                arrayRepeat(someStringField(), 5).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array replace with array access`() {
        val expected = "SELECT ARRAY_REPLACE(`stringArrayField`, \"abc\", \"def\", 3)[0]"

        val actual = QueryBuilder
            .select(
                arrayReplace(someStringArrayField(), "abc", "def", 3).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array reverse with array access`() {
        val expected = "SELECT ARRAY_REVERSE(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayReverse(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sort with array access`() {
        val expected = "SELECT ARRAY_SORT(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arraySort(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sum with addition`() {
        val expected = "SELECT (ARRAY_SUM(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                arraySum(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack on subquery with object function`() {
        val subquery = someObjectSelectRawClause().from(someBucket())
        val expected = "SELECT (SELECT RAW `objectField` FROM `someBucket`)[*].`key`"

        val actual = QueryBuilder
            .select(
                subquery.unpack().getNumberArray("key"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference with array access`() {
        val expected = "SELECT ARRAY_SYMDIFF(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arraySymDiff(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference n (odd) with array access`() {
        val expected = "SELECT ARRAY_SYMDIFFN(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arraySymDiffN(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array union with array access`() {
        val expected = "SELECT ARRAY_UNION(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                arrayUnion(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
