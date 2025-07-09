package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someObjectArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.getNumber
import ch.ergon.dope.resolvable.expression.type.getString
import ch.ergon.dope.resolvable.expression.type.range.filter
import ch.ergon.dope.resolvable.expression.type.range.filterIndexed
import ch.ergon.dope.resolvable.expression.type.range.filterIndexedUnnested
import ch.ergon.dope.resolvable.expression.type.range.map
import ch.ergon.dope.resolvable.expression.type.range.mapIndexed
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RangeTransformationsTest {
    @Test
    fun `should filter and transform array`() {
        val expected = "SELECT ARRAY (`iterator1` * `iterator1`) FOR `iterator1` IN `numberArrayField` " +
            "WHEN `iterator1` <= 2 END[0] = 9"

        val actual = QueryBuilder.select(
            someNumberArrayField().filter { it.isLessOrEqualThan(2) }
                .map { it.mul(it) }.get(0).isEqualTo(9),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should filter and transform array with indices`() {
        val expected = "SELECT ARRAY (`it` * `it`) FOR `i`:`it` IN `numberArrayField` " +
            "WHEN `i` <= 2 END[0] = 9"

        val actual = QueryBuilder.select(
            someNumberArrayField().filterIndexed(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }.map { _, it ->
                it.mul(it)
            }.get(0).isEqualTo(9),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should filter and transform unnested array`() {
        val expected = "SELECT ARRAY `it` FOR `i`:`it` WITHIN `anyTypeArrayField` " +
            "WHEN `i` <= 2 END"

        val actual = QueryBuilder.select(
            someAnyTypeArrayField().filterIndexedUnnested(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            },
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform array and get first instance to use in string function`() {
        val expected = "SELECT CONCAT(\"test\", FIRST `it` FOR `i`:`it` IN `stringArrayField` " +
            "WHEN `i` <= 2 END)"

        val actual = QueryBuilder.select(
            concat(
                "test",
                someStringArrayField().filterIndexed(indexName = "i", iteratorName = "it") { i, _ ->
                    i.isLessOrEqualThan(2)
                }.first(),
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform string into number array and get first instance to use in number function`() {
        val expected = "SELECT (FIRST (TONUMBER(`it`) + `i`) FOR `i`:`it` IN `stringArrayField` END - 5)"

        val actual = QueryBuilder.select(
            someStringArrayField().mapIndexed(iteratorName = "it", indexName = "i") { i, it ->
                it.toNumber().add(i)
            }.first().sub(5),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform array into object and get specific field`() {
        val expected = "SELECT OBJECT CONCAT(\"id:\", TOSTRING(`i`)):`it` FOR `i`:`it` IN `numberArrayField` END.`id:1`"

        val actual = QueryBuilder.select(
            someNumberArrayField().mapIndexed(iteratorName = "it", indexName = "i") { _, it ->
                it
            }.toObject { i, _ ->
                concat("id:", i.toStr())
            }.getNumber("id:1"),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should transform array range with mapping to object`() {
        val expected = "SELECT ARRAY {\"number\" : `it`.`no`, \"id\" : `it`.`id`} FOR `it` IN `objectArrayField` END"

        val actual = QueryBuilder.select(
            someObjectArrayField().map("it") {
                mapOf(
                    "number" to it.getNumber("no"),
                    "id" to it.getString("id"),
                ).toDopeType()
            },
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should use implicit iterator names in range transformations`() {
        val expected = "SELECT ARRAY (`iterator2` * `iterator1`) FOR `iterator1`:`iterator2` IN `numberArrayField` END"

        val actual = QueryBuilder.select(
            someNumberArrayField().mapIndexed { i, it -> it.mul(i) },
        ).build().queryString

        assertEquals(expected, actual)
    }
}
