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
import ch.ergon.dope.resolvable.expression.type.collection.FirstRangeExpression
import ch.ergon.dope.resolvable.expression.type.collection.FirstRangeIndexedExpression
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
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

class FirstRangeTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support first range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.add(1) },
        )

        val actual = range.map(iteratorName) { it.add(1) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { concat(it, "test") },
        )

        val actual = range.map(iteratorName) { concat(it, "test") }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            { it.or(FALSE) },
        )

        val actual = range.map(iteratorName) { it.or(FALSE) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            membershipType = IN,
            range.toDopeType(),
            iteratorName,
            transformation = { it.add(1) },
            condition = { it.isEqualTo(1) },
        )

        val actual = range.filter(iteratorName) { it.isEqualTo(1) }.map { it.add(1) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation = { repeat(it, 1) },
            condition = { contains(it, "test") },
        )

        val actual = range.filter(iteratorName) { contains(it, "test") }.map { repeat(it, 1) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val expected = FirstRangeExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            transformation = { it.or(FALSE) },
            condition = { it },
        )

        val actual = range.filter(iteratorName) { it }.map { it.or(FALSE) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, it -> it.add(i) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, it -> concat(i.toStr(), it) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> concat(i.toStr(), it) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            { i, it -> it.and(i.toBool()) },
        )

        val actual = range.mapIndexed(indexName, iteratorName) { i, it -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            membershipType = IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = { i, it -> it.add(i) },
            condition = { i, it -> it.isEqualTo(i) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> it.isEqualTo(i) }.map { i, it -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = { i, it -> repeat(it, i) },
            condition = { i, it -> contains(it, i.toStr()) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> contains(it, i.toStr()) }.map { i, it -> repeat(it, i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val indexName = "i"
        val iteratorName = "it"
        val expected = FirstRangeIndexedExpression(
            IN,
            range.toDopeType(),
            indexName,
            iteratorName,
            transformation = { i, it -> it.and(i.toBool()) },
            condition = { i, it -> it.or(i.toBool()) },
        )

        val actual = range.filterIndexed(
            indexName,
            iteratorName,
        ) { i, it -> it.or(i.toBool()) }.map { i, it -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
