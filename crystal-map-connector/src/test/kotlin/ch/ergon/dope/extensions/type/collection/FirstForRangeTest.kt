package ch.ergon.dope.extensions.type.collection

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.collection.filter
import ch.ergon.dope.extension.type.collection.mapIndexed
import ch.ergon.dope.extension.type.collection.mapIndexedUnnested
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FirstForRangeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.FirstForUnnestedRangeExpression
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
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.add(i) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> concat(it, i.toStr()) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> concat(it, i.toStr()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.and(i.toBool()) },
        )

        val actual = range.mapIndexed(iteratorName, indexName) { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.add(i) },
        )

        val actual = range.mapIndexedUnnested(iteratorName, indexName) { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> concat(it, i.toStr()) },
        )

        val actual = range.mapIndexedUnnested(iteratorName, indexName) { it, i -> concat(it, i.toStr()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            { it, i -> it.and(i.toBool()) },
        )

        val actual = range.mapIndexedUnnested(iteratorName, indexName) { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.add(i) },
            condition = { it, i -> it.isEqualTo(i) },
        )

        val actual = range.filter { it, i -> it.isEqualTo(i) }.mapIndexed(
            iteratorName,
            indexName,
        ) { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> repeat(it, i) },
            condition = { it, i -> contains(it, i.toStr()) },
        )

        val actual = range.filter { it, i -> contains(it, i.toStr()) }.mapIndexed(
            iteratorName,
            indexName,
        ) { it, i -> repeat(it, i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.and(i.toBool()) },
            condition = { it, i -> it.or(i.toBool()) },
        )

        val actual = range.filter { it, i -> it.or(i.toBool()) }.mapIndexed(
            iteratorName,
            indexName,
        ) { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with condition with cm number list`() {
        val range = someCMNumberList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.add(i) },
            condition = { it, i -> it.isEqualTo(i) },
        )

        val actual = range.filter { it, i -> it.isEqualTo(i) }.mapIndexedUnnested(
            iteratorName,
            indexName,
        ) { it, i -> it.add(i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with condition with cm string list`() {
        val range = someCMStringList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> repeat(it, i) },
            condition = { it, i -> contains(it, i.toStr()) },
        )

        val actual = range.filter { it, i -> contains(it, i.toStr()) }.mapIndexedUnnested(
            iteratorName,
            indexName,
        ) { it, i -> repeat(it, i) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first unnested range transformation with condition with cm boolean list`() {
        val range = someCMBooleanList()
        val iteratorName = "it"
        val indexName = "i"
        val expected = FirstForUnnestedRangeExpression(
            range.toDopeType(),
            iteratorName,
            indexName,
            transformation = { it, i -> it.and(i.toBool()) },
            condition = { it, i -> it.or(i.toBool()) },
        )

        val actual = range.filter { it, i -> it.or(i.toBool()) }.mapIndexedUnnested(
            iteratorName,
            indexName,
        ) { it, i -> it.and(i.toBool()) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
