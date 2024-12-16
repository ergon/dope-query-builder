package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstForRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support first for in expression`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST (`it` * `it`) FOR `iterator1`:`it` IN [1, 2, 3] END",
        )
        val underTest = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression string function`() {
        val range = listOf("test1".toDopeType(), "test2".toDopeType(), "test3".toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\"test\", `it`) FOR `iterator1`:`it` IN [\"test1\", \"test2\", \"test3\"] END",
        )
        val underTest = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression resulting in new type`() {
        val range = listOf("1".toDopeType(), "2".toDopeType(), "3".toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST (`i` * TONUMBER(`it`)) FOR `i`:`it` IN [\"1\", \"2\", \"3\"] END",
        )
        val underTest = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression with condition`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = DopeQuery(
            "FIRST (`iterator1` + 1) FOR `i`:`iterator1` IN [1, 2, 3] WHEN `i` <= 2 END",
        )
        val underTest = FirstForRangeExpression(
            range = range,
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "first"
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\$1, `it`) FOR `iterator1`:`it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = FirstForRangeExpression(
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { it, _ -> concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested first for in expression with condition`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = DopeQuery(
            "FIRST (`it` + `i`) FOR `i`:`it` IN [1, 2, 3] " +
                "WHEN FIRST `it2` FOR `i2`:`it2` IN [1, 2, 3] END = `it` END",
        )
        val underTest = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> it.add(i) },
            condition = { it, _ ->
                FirstForRangeExpression(
                    range = range,
                    iteratorName = "it2",
                    indexName = "i2",
                    transformation = { it2, _ -> it2 },
                ).isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression extension`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
        )

        val actual = range.mapIndexed(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for in expression extension with condition`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = FirstForRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filter {
                _, i ->
            i.isLessOrEqualThan(2)
        }.mapIndexed(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for within expression`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST (`it` * `it`) FOR `iterator1`:`it` WITHIN [1, 2, 3] END",
        )
        val underTest = FirstForUnnestedRangeExpression(
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression string function`() {
        val range = listOf("test1".toDopeType(), "test2".toDopeType(), "test3".toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\"test\", `it`) FOR `iterator1`:`it` WITHIN [\"test1\", \"test2\", \"test3\"] END",
        )
        val underTest = FirstForUnnestedRangeExpression(
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression resulting in new type`() {
        val range = listOf("1".toDopeType(), "2".toDopeType(), "3".toDopeType()).toDopeType()
        val expected = DopeQuery(
            queryString = "FIRST (`i` * TONUMBER(`it`)) FOR `i`:`it` WITHIN [\"1\", \"2\", \"3\"] END",
        )
        val underTest = FirstForUnnestedRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression with condition`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = DopeQuery(
            "FIRST (`iterator1` + 1) FOR `i`:`iterator1` WITHIN [1, 2, 3] WHEN `i` <= 2 END",
        )
        val underTest = FirstForUnnestedRangeExpression(
            range = range,
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "first"
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\$1, `it`) FOR `iterator1`:`it` WITHIN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = FirstForUnnestedRangeExpression(
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { it, _ -> concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression extension`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = FirstForUnnestedRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
        )

        val actual = range.mapIndexedUnnested(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for within expression extension with condition`() {
        val range = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val expected = FirstForUnnestedRangeExpression(
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filter {
                _, i ->
            i.isLessOrEqualThan(2)
        }.mapIndexedUnnested(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
