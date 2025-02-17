package ch.ergon.dope.resolvable.expression.unaliased.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toStr
import ch.ergon.dope.resolvable.expression.unaliased.type.getNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectForRangeIndexedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object for in indexed expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`iterator1`):(`it` * `it`) FOR `iterator1`:`it` IN `numberArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in indexed expression string function`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`iterator1`):CONCAT(\"test\", `it`) FOR `iterator1`:`it` IN `stringArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in indexed expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`i`):(`i` * TONUMBER(`it`)) FOR `i`:`it` IN `stringArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in indexed expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`i`):(`iterator1` + 1) FOR `i`:`iterator1` IN `numberArrayField` WHEN `i` <= 2 END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in indexed expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "object"
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`iterator1`):CONCAT(\$1, `it`) FOR `iterator1`:`it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested object for in indexed expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`i`):(`it` + `i`) FOR `i`:`it` IN `numberArrayField` " +
                "WHEN OBJECT TOSTRING(`i2`):`it2` FOR `i2`:`it2` IN `numberArrayField` END.`i` = `it` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, i -> it.add(i) },
            condition = { it, _ ->
                ObjectForRangeIndexedExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    indexName = "i2",
                    withAttributeKeys = { _, i2 -> i2.toStr() },
                    transformation = { it2, _ -> it2 },
                ).getNumber("i").isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in indexed expression extension`() {
        val range = someNumberArrayField()
        val expected = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.add(1) },
        )

        val actual = range.mapIndexed(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.add(1)
        }.toObject {
                _, i ->
            i.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for in indexed expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = ObjectForRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filterIndexed(iteratorName = "it", indexName = "i") {
                _, i ->
            i.isLessOrEqualThan(2)
        }.map {
                it, _ ->
            it.add(1)
        }.toObject {
                _, i ->
            i.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for within indexed expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`iterator1`):(`it` * `it`) FOR `iterator1`:`it` WITHIN `numberArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within indexed expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`iterator1`):CONCAT(\"test\", TOSTRING(`it`)) " +
                "FOR `iterator1`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> concat("test", it.toStr()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within indexed expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`i`):(`i` * TONUMBER(`it`)) FOR `i`:`it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, i -> i.mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within indexed expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`i`):(TONUMBER(`iterator1`) + 1) FOR `i`:`iterator1` WITHIN `anyTypeArrayField` WHEN `i` <= 2 END",
        )
        val underTest = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            indexName = "i",
            transformation = { it, _ -> it.toNumber().add(1) },
            withAttributeKeys = { _, i -> i.toStr() },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within indexed expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.toNumber().add(1) },
        )

        val actual = range.mapIndexedUnnested(iteratorName = "it", indexName = "i") {
                it, _ ->
            it.toNumber().add(1)
        }.toObject {
                _, i ->
            i.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for within indexed expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = ObjectForRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            withAttributeKeys = { _, i -> i.toStr() },
            transformation = { it, _ -> it.toNumber().add(1) },
            condition = { _, i -> i.isLessOrEqualThan(2) },
        )

        val actual = range.filterIndexedUnnested(iteratorName = "it", indexName = "i") {
                _, i ->
            i.isLessOrEqualThan(2)
        }.map {
                it, _ ->
            it.toNumber().add(1)
        }.toObject {
                _, i ->
            i.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
