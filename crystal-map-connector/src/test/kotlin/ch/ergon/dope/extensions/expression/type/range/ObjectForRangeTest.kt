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
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.ObjectRangeExpression
import ch.ergon.dope.resolvable.expression.type.range.ObjectRangeIndexedExpression
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectForRangeTest {
    @Test
    fun `should support array range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<NumberType>) -> TypeExpression<NumberType> = { it.add(1) }
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range.map(iteratorName, transformation).toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<StringType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<StringType>) -> TypeExpression<StringType> = { it.concat("test") }
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range.map(iteratorName, transformation).toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<BooleanType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it.or(FALSE) }
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range.map(iteratorName, transformation).toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<NumberType>) -> TypeExpression<NumberType> = { it.add(1) }
        val condition: (Iterator<NumberType>) -> TypeExpression<BooleanType> = { it.isEqualTo(1) }
        val expected = ObjectRangeExpression(
            membershipType = IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range.filter(iteratorName, condition).map(transformation).toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<StringType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<StringType>) -> TypeExpression<StringType> = { it.repeat(1) }
        val condition: (Iterator<StringType>) -> TypeExpression<BooleanType> = { it.contains("test") }
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range
            .filter(iteratorName, condition)
            .map(transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<BooleanType>) -> TypeExpression<StringType> = { it.toStr() }
        val transformation: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it.or(FALSE) }
        val condition: (Iterator<BooleanType>) -> TypeExpression<BooleanType> = { it }
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range
            .filter(iteratorName, condition)
            .map(transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<NumberType> =
            { i, it -> it.add(i) }
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range
            .mapIndexed(indexName, iteratorName, transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, it -> it.concat(i.toStr()) }
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range
            .mapIndexed(indexName, iteratorName, transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.and(i.toBool()) }
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation,
        )

        val actual = range
            .mapIndexed(indexName, iteratorName, transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<NumberType> =
            { i, it -> it.add(i) }
        val condition: (Iterator<NumberType>, Iterator<NumberType>) -> TypeExpression<BooleanType> =
            { i, it -> it.isEqualTo(i) }
        val expected = ObjectRangeIndexedExpression(
            membershipType = IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range
            .filterIndexed(indexName, iteratorName, condition)
            .map(transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<StringType> =
            { i, it -> it.repeat(i) }
        val condition: (Iterator<NumberType>, Iterator<StringType>) -> TypeExpression<BooleanType> =
            { i, it -> it.contains(i.toStr()) }
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range
            .filterIndexed(indexName, iteratorName, condition)
            .map(transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support indexed array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val withAttributeKeys: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<StringType> =
            { i, _ -> i.toStr() }
        val transformation: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.and(i.toBool()) }
        val condition: (Iterator<NumberType>, Iterator<BooleanType>) -> TypeExpression<BooleanType> =
            { i, it -> it.or(i.toBool()) }
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            withAttributeKeys,
            transformation = transformation,
            condition = condition,
        )

        val actual = range
            .filterIndexed(indexName, iteratorName, condition)
            .map(transformation)
            .toObject(withAttributeKeys)

        assertEquals(expected, actual)
    }
}
