package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.type.isNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support first for in expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "FIRST (`it` * `it`) FOR `it` IN `numberArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression string function`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\"test\", `it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "FIRST TONUMBER(`it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "FIRST (`iterator1` + 1) FOR `iterator1` IN `numberArrayField` WHEN `iterator1` <= 2 END",
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range,
            transformation = { it.add(1) },
            condition = { it.isLessOrEqualThan(2) },
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
            queryString = "FIRST CONCAT(\$1, `it`) FOR `it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested first for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "FIRST `it` FOR `it` IN `numberArrayField` " +
                "WHEN FIRST `it2` FOR `it2` IN `numberArrayField` END = `it` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it },
            condition = {
                FirstRangeExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    transformation = { it2 -> it2 },
                ).isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for in expression extension`() {
        val range = someNumberArrayField()
        val expected = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.add(1) },
        )

        val actual = range.map(iteratorName = "it") { it.add(1) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for in expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = FirstRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.add(1) },
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = range.filter(iteratorName = "it") {
            it.isLessOrEqualThan(2)
        }.map {
            it.add(1)
        }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for within expression`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "FIRST (TONUMBER(`it`) * TONUMBER(`it`)) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber().mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "FIRST CONCAT(\"test\", TOSTRING(`it`)) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { concat("test", it.toStr()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for WITHIN expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "FIRST TONUMBER(`it`) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            "FIRST (TONUMBER(`iterator1`) + 1) FOR `iterator1` WITHIN `anyTypeArrayField` WHEN ISNUMBER(`iterator1`) END",
        )
        val underTest = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            transformation = { it.toNumber().add(1) },
            condition = { it.isNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first for within expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = range.mapUnnested(iteratorName = "it") { it.toNumber() }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first for within expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = FirstRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber().add(1) },
            condition = { it.isNumber() },
        )

        val actual = range.filterUnnested(iteratorName = "it") { it.isNumber() }.map { it.toNumber().add(1) }.first()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
