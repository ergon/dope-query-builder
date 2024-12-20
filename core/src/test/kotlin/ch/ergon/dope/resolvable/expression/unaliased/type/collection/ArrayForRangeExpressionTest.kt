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
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toStr
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayForRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support array for in expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (`it` * `it`) FOR `it` IN `numberArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression string function`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY CONCAT(\"test\", `it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY TONUMBER(`it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "ARRAY (`iterator1` + 1) FOR `iterator1` IN `numberArrayField` WHEN `iterator1` <= 2 END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            transformation = { it.add(1) },
            condition = { it.isLessOrEqualThan(2) },
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
            queryString = "ARRAY CONCAT(\$1, `it`) FOR `it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            transformation = { concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested array for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "ARRAY `it` FOR `it` IN `numberArrayField` " +
                "WHEN ARRAY `it2` FOR `it2` IN `numberArrayField` END[0] = `it` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it },
            condition = {
                ArrayForRangeExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    transformation = { it2 -> it2 },
                ).get(0).isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for in expression extension`() {
        val range = someNumberArrayField()
        val expected = ArrayForRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            transformation = { it.add(1) },
        )

        val actual = range.map(iteratorName = "it") { it.add(1) }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for in expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = ArrayForRangeExpression(
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
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for within expression`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY (TONUMBER(`it`) * TONUMBER(`it`)) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber().mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY CONCAT(\"test\", TOSTRING(`it`)) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { concat("test", it.toStr()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for WITHIN expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "ARRAY TONUMBER(`it`) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            "ARRAY (TONUMBER(`iterator1`) + 1) FOR `iterator1` WITHIN `anyTypeArrayField` WHEN ISNUMBER(`iterator1`) END",
        )
        val underTest = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            transformation = { it.toNumber().add(1) },
            condition = { it.isNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array for within expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber() },
        )

        val actual = range.mapUnnested(iteratorName = "it") { it.toNumber() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array for within expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = ArrayForRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            transformation = { it.toNumber().add(1) },
            condition = { it.isNumber() },
        )

        val actual = range.filterUnnested(iteratorName = "it") { it.isNumber() }.map { it.toNumber().add(1) }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
