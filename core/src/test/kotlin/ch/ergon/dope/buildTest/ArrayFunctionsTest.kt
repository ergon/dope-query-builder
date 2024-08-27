package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAppend
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAverage
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayBinarySearch
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayConcat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayCount
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayDistinct
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayExcept
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayFlatten
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayIfNull
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayInsert
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayIntersect
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayMax
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayMin
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayMove
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayPosition
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayPrepend
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayPut
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayRange
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayRemove
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayRepeat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayReplace
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayReverse
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySort
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySum
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySymDiff
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySymDiffN
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayUnion
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.lower
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support array append with array access`() {
        val expected = "SELECT ARRAY_APPEND(`stringArrayField`, `stringField`)[0]"

        val actual = create
            .select(
                arrayAppend(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array average with addition`() {
        val expected = "SELECT (ARRAY_AVG(`numberArrayField`) + 1)"

        val actual = create
            .select(
                arrayAverage(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array binary search with addition`() {
        val expected = "SELECT (ARRAY_BINARY_SEARCH(`numberArrayField`, `numberField`) + 1)"

        val actual = create
            .select(
                arrayBinarySearch(someNumberArrayField(), someNumberField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array concat with array access`() {
        val expected = "SELECT ARRAY_CONCAT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arrayConcat(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array contains with boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ARRAY_CONTAINS(`stringArrayField`, `stringField`)"

        val actual = create
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

        val actual = create
            .select(
                arrayCount(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array distinct with array access`() {
        val expected = "SELECT ARRAY_DISTINCT(`stringArrayField`)[0]"

        val actual = create
            .select(
                arrayDistinct(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array except with array access`() {
        val expected = "SELECT ARRAY_EXCEPT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arrayExcept(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array flatten with array access`() {
        val expected = "SELECT ARRAY_FLATTEN(`stringArrayField`, 2)[0]"

        val actual = create
            .select(
                arrayFlatten(someStringArrayField(), 2).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array if null with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_IFNULL(`stringArrayField`))"

        val actual = create
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

        val actual = create
            .select(
                arrayInsert(someStringArrayField(), 0, someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array intersect with array access`() {
        val expected = "SELECT ARRAY_INTERSECT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arrayIntersect(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array length with addition`() {
        val expected = "SELECT (ARRAY_LENGTH(`numberArrayField`) + 1)"

        val actual = create
            .select(
                arrayLength(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array max with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_MAX(`stringArrayField`))"

        val actual = create
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

        val actual = create
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

        val actual = create
            .select(
                arrayMove(someStringArrayField(), 0, 1).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array position with addition`() {
        val expected = "SELECT (ARRAY_POSITION(`stringArrayField`, `stringField`) + 1)"

        val actual = create
            .select(
                arrayPosition(someStringArrayField(), someStringField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array prepend with array access`() {
        val expected = "SELECT ARRAY_PREPEND(`stringField`, `stringArrayField`)[0]"

        val actual = create
            .select(
                arrayPrepend(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array put with array access`() {
        val expected = "SELECT ARRAY_PUT(`stringArrayField`, `stringField`)[0]"

        val actual = create
            .select(
                arrayPut(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range with array access`() {
        val expected = "SELECT ARRAY_RANGE(0, 10, 2)[0]"

        val actual = create
            .select(
                arrayRange(0, 10, 2).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array remove with array access`() {
        val expected = "SELECT ARRAY_REMOVE(`stringArrayField`, `stringField`)[0]"

        val actual = create
            .select(
                arrayRemove(someStringArrayField(), someStringField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array repeat with array access`() {
        val expected = "SELECT ARRAY_REPEAT(`stringField`, 5)[0]"

        val actual = create
            .select(
                arrayRepeat(someStringField(), 5).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array replace with array access`() {
        val expected = "SELECT ARRAY_REPLACE(`stringArrayField`, \"abc\", \"def\", 3)[0]"

        val actual = create
            .select(
                arrayReplace(someStringArrayField(), "abc", "def", 3).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array reverse with array access`() {
        val expected = "SELECT ARRAY_REVERSE(`stringArrayField`)[0]"

        val actual = create
            .select(
                arrayReverse(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sort with array access`() {
        val expected = "SELECT ARRAY_SORT(`stringArrayField`)[0]"

        val actual = create
            .select(
                arraySort(someStringArrayField()).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sum with addition`() {
        val expected = "SELECT (ARRAY_SUM(`numberArrayField`) + 1)"

        val actual = create
            .select(
                arraySum(someNumberArrayField()).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference with array access`() {
        val expected = "SELECT ARRAY_SYMDIFF(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arraySymDiff(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference n (odd) with array access`() {
        val expected = "SELECT ARRAY_SYMDIFFN(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arraySymDiffN(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array union with array access`() {
        val expected = "SELECT ARRAY_UNION(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = create
            .select(
                arrayUnion(someStringArrayField(), someStringArrayField("anotherStringArrayField")).get(0),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
