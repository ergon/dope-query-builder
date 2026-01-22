package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someObjectSelectRawClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.function.array.append
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRange
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.type.function.array.average
import ch.ergon.dope.resolvable.expression.type.function.array.binarySearch
import ch.ergon.dope.resolvable.expression.type.function.array.concat
import ch.ergon.dope.resolvable.expression.type.function.array.contains
import ch.ergon.dope.resolvable.expression.type.function.array.count
import ch.ergon.dope.resolvable.expression.type.function.array.distinct
import ch.ergon.dope.resolvable.expression.type.function.array.except
import ch.ergon.dope.resolvable.expression.type.function.array.flatten
import ch.ergon.dope.resolvable.expression.type.function.array.ifNull
import ch.ergon.dope.resolvable.expression.type.function.array.insert
import ch.ergon.dope.resolvable.expression.type.function.array.intersect
import ch.ergon.dope.resolvable.expression.type.function.array.length
import ch.ergon.dope.resolvable.expression.type.function.array.max
import ch.ergon.dope.resolvable.expression.type.function.array.min
import ch.ergon.dope.resolvable.expression.type.function.array.move
import ch.ergon.dope.resolvable.expression.type.function.array.position
import ch.ergon.dope.resolvable.expression.type.function.array.prepend
import ch.ergon.dope.resolvable.expression.type.function.array.put
import ch.ergon.dope.resolvable.expression.type.function.array.remove
import ch.ergon.dope.resolvable.expression.type.function.array.replace
import ch.ergon.dope.resolvable.expression.type.function.array.reverse
import ch.ergon.dope.resolvable.expression.type.function.array.sort
import ch.ergon.dope.resolvable.expression.type.function.array.sum
import ch.ergon.dope.resolvable.expression.type.function.array.symDiff
import ch.ergon.dope.resolvable.expression.type.function.array.symDiffN
import ch.ergon.dope.resolvable.expression.type.function.array.union
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
                someStringArrayField().append(someStringField()).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array average with addition`() {
        val expected = "SELECT (ARRAY_AVG(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                someNumberArrayField().average().add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array binary search with addition`() {
        val expected = "SELECT (ARRAY_BINARY_SEARCH(`numberArrayField`, `numberField`) + 1)"

        val actual = QueryBuilder
            .select(
                someNumberArrayField().binarySearch(someNumberField()).add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array concat with array access`() {
        val expected = "SELECT ARRAY_CONCAT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().concat(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array contains with boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ARRAY_CONTAINS(`stringArrayField`, `stringField`)"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someKeyspace(),
            )
            .where(
                someStringArrayField().contains(someStringField()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array count with addition`() {
        val expected = "SELECT (ARRAY_COUNT(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                someNumberArrayField().count().add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array distinct with array access`() {
        val expected = "SELECT ARRAY_DISTINCT(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().distinct().get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array except with array access`() {
        val expected = "SELECT ARRAY_EXCEPT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().except(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array flatten with array access`() {
        val expected = "SELECT ARRAY_FLATTEN(`stringArrayField`, 2)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().flatten(2).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array if null with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_IFNULL(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    someStringArrayField().ifNull(),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array insert with array access`() {
        val expected = "SELECT ARRAY_INSERT(`stringArrayField`, 0, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().insert(0, someStringField()).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array intersect with array access`() {
        val expected = "SELECT ARRAY_INTERSECT(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().intersect(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array length with addition`() {
        val expected = "SELECT (ARRAY_LENGTH(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                someNumberArrayField().length().add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array max with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_MAX(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    someStringArrayField().max(),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array min with element specific function`() {
        val expected = "SELECT LOWER(ARRAY_MIN(`stringArrayField`))"

        val actual = QueryBuilder
            .select(
                lower(
                    someStringArrayField().min(),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array move with array access`() {
        val expected = "SELECT ARRAY_MOVE(`stringArrayField`, 0, 1)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().move(0, 1).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array position with addition`() {
        val expected = "SELECT (ARRAY_POSITION(`stringArrayField`, `stringField`) + 1)"

        val actual = QueryBuilder
            .select(
                someStringArrayField().position(someStringField()).add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array prepend with array access`() {
        val expected = "SELECT ARRAY_PREPEND(`stringField`, `stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().prepend(someStringField()).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array put with array access`() {
        val expected = "SELECT ARRAY_PUT(`stringArrayField`, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().put(someStringField()).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range with array access`() {
        val expected = "SELECT ARRAY_RANGE(0, 10, 2)[0]"

        val actual = QueryBuilder
            .select(
                arrayRange(0, 10, 2).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array remove with array access`() {
        val expected = "SELECT ARRAY_REMOVE(`stringArrayField`, `stringField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().remove(someStringField()).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array repeat with array access`() {
        val expected = "SELECT ARRAY_REPEAT(`stringField`, 5)[0]"

        val actual = QueryBuilder
            .select(
                arrayRepeat(someStringField(), 5).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array replace with array access`() {
        val expected = "SELECT ARRAY_REPLACE(`stringArrayField`, \"abc\", \"def\", 3)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().replace("abc", "def", 3).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array reverse with array access`() {
        val expected = "SELECT ARRAY_REVERSE(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().reverse().get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sort with array access`() {
        val expected = "SELECT ARRAY_SORT(`stringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().sort().get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array sum with addition`() {
        val expected = "SELECT (ARRAY_SUM(`numberArrayField`) + 1)"

        val actual = QueryBuilder
            .select(
                someNumberArrayField().sum().add(1),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unpack on subquery with object function`() {
        val subquery = someObjectSelectRawClause().from(someKeyspace())
        val expected = "SELECT (SELECT RAW `objectField` FROM `someBucket`)[*].`key`"

        val actual = QueryBuilder
            .select(
                subquery.unpack().getNumberArray("key"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference with array access`() {
        val expected = "SELECT ARRAY_SYMDIFF(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().symDiff(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array symmetric difference n (odd) with array access`() {
        val expected = "SELECT ARRAY_SYMDIFFN(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().symDiffN(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array union with array access`() {
        val expected = "SELECT ARRAY_UNION(`stringArrayField`, `anotherStringArrayField`)[0]"

        val actual = QueryBuilder
            .select(
                someStringArrayField().union(someStringArrayField("anotherStringArrayField")).get(0),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
