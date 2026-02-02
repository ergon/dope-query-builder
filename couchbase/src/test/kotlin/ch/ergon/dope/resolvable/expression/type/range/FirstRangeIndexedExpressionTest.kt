package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstRangeIndexedExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support first for in expression`() {
        val range = someNumberArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST (`it` * `it`) FOR `iterator1`:`it` IN `numberArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { _, it -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression string function`() {
        val range = someStringArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST CONCAT(\"test\", `it`) FOR `iterator1`:`it` IN `stringArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { _, it -> "test".toDopeType().concat(it) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST (`i` * TONUMBER(`it`)) FOR `i`:`it` IN `stringArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { i, it -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = CouchbaseDopeQuery(
            "FIRST (`iterator1` + 1) FOR `i`:`iterator1` IN `numberArrayField` WHEN `i` <= 2 END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            indexName = "i",
            transformation = { _, it -> it.add(1) },
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "first"
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST CONCAT(\$1, `it`) FOR `iterator1`:`it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { _, it -> positionalParameterValue.asParameter().concat(it) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested first for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = CouchbaseDopeQuery(
            "FIRST (`it` + `i`) FOR `i`:`it` IN `numberArrayField` " +
                "WHEN FIRST `it2` FOR `i2`:`it2` IN `numberArrayField` END = `it` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { i, it -> it.add(i) },
            condition = { _, it ->
                FirstRangeIndexedExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    indexName = "i2",
                    transformation = { _, it2 -> it2 },
                ).isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression extension`() {
        val range = someNumberArrayField()
        val expected = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { _, it -> it.add(1) },
        )

        val actual =
            range.mapIndexed(iteratorName = "it", indexName = "i") { _, it ->
                it.add(1)
            }.first()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support first for in expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = FirstRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { _, it -> it.add(1) },
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexed(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }.map { _, it ->
                it.add(1)
            }.first()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support first for within expression`() {
        val range = someAnyTypeArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST (TONUMBER(`it`) * TONUMBER(`it`)) FOR `iterator1`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { _, it -> it.toNumber().mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST CONCAT(\"test\", TOSTRING(`it`)) FOR `iterator1`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { _, it -> "test".toDopeType().concat(it.toStr()) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "FIRST (`i` * TONUMBER(`it`)) FOR `i`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { i, it -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = CouchbaseDopeQuery(
            "FIRST (TONUMBER(`iterator1`) + 1) FOR `i`:`iterator1` WITHIN `anyTypeArrayField` WHEN `i` <= 2 END",
        )
        val underTest = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            indexName = "i",
            transformation = { _, it -> it.toNumber().add(1) },
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { _, it -> it.toNumber().add(1) },
        )

        val actual =
            range.mapIndexedUnnested(iteratorName = "it", indexName = "i") { _, it ->
                it.toNumber().add(1)
            }.first()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support first for within expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = FirstRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            transformation = { _, it -> it.toNumber().add(1) },
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexedUnnested(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }.map { _, it ->
                it.toNumber().add(1)
            }.first()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
