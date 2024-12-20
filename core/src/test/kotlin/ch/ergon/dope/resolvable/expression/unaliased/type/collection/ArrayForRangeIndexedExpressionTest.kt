package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toStr
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayForRangeIndexedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support array for in expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (`it` * `it`) FOR `iterator1`:`it` IN `numberArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression string function`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY CONCAT(\"test\", `it`) FOR `iterator1`:`it` IN `stringArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (`i` * TONUMBER(`it`)) FOR `i`:`it` IN `stringArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "ARRAY (`iterator1` + 1) FOR `i`:`iterator1` IN `numberArrayField` WHEN `i` <= 2 END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "array"
        val expected = DopeQuery(
            queryString = "ARRAY CONCAT(\$1, `it`) FOR `iterator1`:`it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { it, _ -> concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested array for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "ARRAY (`it` + `i`) FOR `i`:`it` IN `numberArrayField` " +
                "WHEN ARRAY `it2` FOR `i2`:`it2` IN `numberArrayField` END[`i`] = `it` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> it.add(i) },
            condition = { it, i ->
                ArrayForRangeIndexedExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    indexName = "i2",
                    transformation = { it2, _ -> it2 },
                ).get(i).isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression extension`() {
        val range = someNumberArrayField()
        val expected = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
        )

        val actual = range.mapIndexed(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for in expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = ArrayForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filterIndexed(iteratorName = "it", indexName = "i") {
                _, i ->
            i.isLessOrEqualThan(2)
        }.map {
                it, _ ->
            it.add(1)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for within expression`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (TONUMBER(`it`) * TONUMBER(`it`)) FOR `iterator1`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> it.toNumber().mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY CONCAT(\"test\", TOSTRING(`it`)) FOR `iterator1`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it, _ -> concat("test", it.toStr()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for WITHIN expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (`i` * TONUMBER(`it`)) FOR `i`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            "ARRAY (TONUMBER(`iterator1`) + 1) FOR `i`:`iterator1` WITHIN `anyTypeArrayField` WHEN `i` <= 2 END",
        )
        val underTest = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            indexName = "i",
            transformation = { it, _ -> it.toNumber().add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.toNumber() },
        )

        val actual = range.mapIndexedUnnested(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.toNumber()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for within expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = ArrayForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { it, _ -> it.toNumber().add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filterIndexedUnnested(iteratorName = "it", indexName = "i") {
                _, i ->
            i.isLessOrEqualThan(2)
        }.map {
                it, _ ->
            it.toNumber().add(1)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
