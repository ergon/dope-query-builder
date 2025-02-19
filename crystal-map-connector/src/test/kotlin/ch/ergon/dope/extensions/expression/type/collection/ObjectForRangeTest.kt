package ch.ergon.dope.extensions.expression.type.collection

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.collection.filter
import ch.ergon.dope.extension.expression.type.collection.filterIndexed
import ch.ergon.dope.extension.expression.type.collection.map
import ch.ergon.dope.extension.expression.type.collection.mapIndexed
import ch.ergon.dope.extension.expression.type.function.string.concat
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.collection.ObjectRangeExpression
import ch.ergon.dope.resolvable.expression.type.collection.ObjectRangeIndexedExpression
import ch.ergon.dope.resolvable.expression.type.function.string.contains
import ch.ergon.dope.resolvable.expression.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.type.function.type.toBool
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectForRangeTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support array range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            { it.add(1) },
        )

        val actual = range.map(iteratorName) { it.add(1) }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            { concat(it, "test") },
        )

        val actual = range.map(iteratorName) { concat(it, "test") }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            { it.or(FALSE) },
        )

        val actual = range.map(iteratorName) { it.or(FALSE) }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            membershipType = IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            transformation = { it.add(1) },
            condition = { it.isEqualTo(1) },
        )

        val actual = range.filter(iteratorName) { it.isEqualTo(1) }.map { it.add(1) }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            transformation = { repeat(it, 1) },
            condition = { contains(it, "test") },
        )

        val actual = range.filter(iteratorName) { contains(it, "test") }.map { repeat(it, 1) }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val expected = ObjectRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.toStr() },
            transformation = { it.or(FALSE) },
            condition = { it },
        )

        val actual = range.filter(iteratorName) { it }.map { it.or(FALSE) }.toObject { it.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            { i, it -> it.add(i) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> it.add(i) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            { i, it -> concat(it, i.toStr()) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> concat(it, i.toStr()) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            { i, it -> it.and(i.toBool()) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> it.and(i.toBool()) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            membershipType = IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            transformation = { i, it -> it.add(i) },
            condition = { i, it -> it.isEqualTo(i) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> it.isEqualTo(i) }.map { i, it -> it.add(i) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            transformation = { i, it -> repeat(it, i) },
            condition = { i, it -> contains(it, i.toStr()) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> contains(it, i.toStr()) }.map { i, it -> repeat(it, i) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed array range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = ObjectRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, _ -> i.toStr() },
            transformation = { i, it -> it.and(i.toBool()) },
            condition = { i, it -> it.or(i.toBool()) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> it.or(i.toBool()) }.map { i, it -> it.and(i.toBool()) }.toObject { i, _ -> i.toStr() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
