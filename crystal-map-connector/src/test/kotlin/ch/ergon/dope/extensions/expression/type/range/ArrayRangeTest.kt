package ch.ergon.dope.extensions.expression.type.range

import ch.ergon.dope.extension.expression.type.function.string.concat
import ch.ergon.dope.extension.expression.type.range.filter
import ch.ergon.dope.extension.expression.type.range.filterIndexed
import ch.ergon.dope.extension.expression.type.range.map
import ch.ergon.dope.extension.expression.type.range.mapIndexed
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.function.string.contains
import ch.ergon.dope.resolvable.expression.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.type.function.type.toBool
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.range.ArrayRangeExpression
import ch.ergon.dope.resolvable.expression.type.range.ArrayRangeIndexedExpression
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRangeTest {
    @Test
    fun `should support array range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>) -> TypeExpression<NumberType> = { it.add(1) }
        val expected = ArrayRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation,
        )

        val actual = range.map(iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val transformation: (Iterator<StringType>) -> TypeExpression<StringType> = { it.concat("test") }
        val expected = ArrayRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation,
        )

        val actual = range.map(iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val transformation: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it.or(FALSE) }
        val expected = ArrayRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation,
        )

        val actual = range.map(iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>) -> TypeExpression<NumberType> = { it.add(1) }
        val condition: (Iterator<NumberType>) -> TypeExpression<BooleanType> = { it.isEqualTo(1) }
        val expected = ArrayRangeExpression(
            membershipType = IN,
            range.toDopeType(),
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filter(iteratorName, condition).map(transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val transformation: (Iterator<StringType>) -> TypeExpression<StringType> = { it.repeat(1) }
        val condition: (Iterator<StringType>) -> TypeExpression<BooleanType> = { it.contains("test") }
        val expected = ArrayRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filter(iteratorName, condition).map(transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val transformation: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it.or(FALSE) }
        val condition: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it }
        val expected = ArrayRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filter(iteratorName, condition).map(transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<NumberType> =
            { i, it -> it.add(i) }
        val expected = ArrayRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation,
        )

        val actual = range.mapIndexed(indexName, iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, it -> i.toStr().concat(it) }
        val expected = ArrayRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation,
        )

        val actual = range.mapIndexed(indexName, iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.and(i.toBool()) }
        val expected = ArrayRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation,
        )

        val actual = range.mapIndexed(indexName, iteratorName, transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<NumberType> =
            { i, it -> it.add(i) }
        val condition: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<BooleanType> =
            { i, it -> it.isEqualTo(i) }
        val expected = ArrayRangeIndexedExpression(
            membershipType = IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
            condition,
        ).map(transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, it -> it.repeat(i) }
        val condition: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<BooleanType> =
            { i, it -> i.toStr().contains(it) }
        val expected = ArrayRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
            condition,
        ).map(transformation)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val transformation: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.and(i.toBool()) }
        val condition: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.or(i.toBool()) }
        val expected = ArrayRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
            condition,
        ).map(transformation)

        assertEquals(expected, actual)
    }
}
