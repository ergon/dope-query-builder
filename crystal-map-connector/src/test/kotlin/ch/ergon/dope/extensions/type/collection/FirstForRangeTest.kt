package ch.ergon.dope.extensions.type.collection

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.collection.filter
import ch.ergon.dope.extension.type.collection.filterIndexed
import ch.ergon.dope.extension.type.collection.map
import ch.ergon.dope.extension.type.collection.mapIndexed
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.type.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FirstForRangeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FirstForRangeIndexedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.contains
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.repeat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toBool
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toStr
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstForRangeTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support first range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val expected = FirstForRangeExpression(
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
        val expected = FirstForRangeExpression(
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
        val expected = FirstForRangeExpression(
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
        val expected = FirstForRangeExpression(
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
        val expected = FirstForRangeExpression(
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
        val expected = FirstForRangeExpression(
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
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.add(i) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> concat(it, i.toStr()) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> concat(it, i.toStr()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.and(i.toBool()) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            membershipType = IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.add(i) },
            condition = { it, i -> it.isEqualTo(i) },
        )

        val actual = range.filterIndexed(
            iteratorName,
            indexName,
        ) { it, i -> it.isEqualTo(i) }.map { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> repeat(it, i) },
            condition = { it, i -> contains(it, i.toStr()) },
        )

        val actual = range.filterIndexed(
            iteratorName,
            indexName,
        ) { it, i -> contains(it, i.toStr()) }.map { it, i -> repeat(it, i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support indexed first range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeIndexedExpression(
            IN,
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.and(i.toBool()) },
            condition = { it, i -> it.or(i.toBool()) },
        )

        val actual = range.filterIndexed(
            iteratorName,
            indexName,
        ) { it, i -> it.or(i.toBool()) }.map { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
