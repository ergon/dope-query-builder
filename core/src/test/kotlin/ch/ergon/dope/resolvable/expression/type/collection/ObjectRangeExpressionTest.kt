package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.getNumber
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support object for in expression`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`it`):(`it` * `it`) FOR `it` IN `numberArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it.toStr() },
            transformation = { it.mul(it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in expression string function`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT `it`:CONCAT(\"test\", `it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it },
            transformation = { concat("test", it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in expression resulting in new type`() {
        val range = someStringArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT `it`:TONUMBER(`it`) FOR `it` IN `stringArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it },
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`iterator1`):(`iterator1` + 1) FOR `iterator1` IN `numberArrayField` WHEN `iterator1` <= 2 END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            withAttributeKeys = { it.toStr() },
            transformation = { it.add(1) },
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in expression with parameters`() {
        val range = listOf("test1", "test2", "test3")
        val positionalParameterValue = "test"
        val namedParameterName = "object"
        val expected = DopeQuery(
            queryString = "OBJECT `it`:CONCAT(\$1, `it`) FOR `it` IN \$$namedParameterName END",
            parameters = DopeParameters(
                namedParameters = mapOf(namedParameterName to range),
                positionalParameters = listOf(positionalParameterValue),
            ),
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range.asParameter(namedParameterName),
            iteratorName = "it",
            withAttributeKeys = { it },
            transformation = { concat(positionalParameterValue.asParameter(), it) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested object for in expression with condition`() {
        val range = someNumberArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`it`):(`it` + 1) FOR `it` IN `numberArrayField` " +
                "WHEN OBJECT TOSTRING(`it2`):`it2` FOR `it2` IN `numberArrayField` END.`1` = `it` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it.toStr() },
            transformation = { it.add(1) },
            condition = {
                ObjectRangeExpression(
                    membershipType = IN,
                    range = range,
                    iteratorName = "it2",
                    withAttributeKeys = { it2 -> it2.toStr() },
                    transformation = { it2 -> it2 },
                ).getNumber("1").isEqualTo(it)
            },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for in expression extension`() {
        val range = someNumberArrayField()
        val expected = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it.toStr() },
            transformation = { it.add(1) },
        )

        val actual = range.map(iteratorName = "it") {
            it.add(1)
        }.toObject {
            it.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for in expression extension with condition`() {
        val range = someNumberArrayField()
        val expected = ObjectRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",

            withAttributeKeys = { it.toStr() },
            transformation = { it.add(1) },
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = range.filter(iteratorName = "it") {
            it.isLessOrEqualThan(2)
        }.map {
            it.add(1)
        }.toObject {
            it.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for within expression`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`it`):(TONUMBER(`it`) * TONUMBER(`it`)) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it.toStr() },
            transformation = { it.toNumber().mul(it.toNumber()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within expression string function`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`it`):CONCAT(\"test\", TOSTRING(`it`)) " +
                "FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            withAttributeKeys = { it.toStr() },
            transformation = { concat("test", it.toStr()) },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within expression resulting in new type`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            queryString = "OBJECT TOSTRING(`it`):TONUMBER(`it`) FOR `it` WITHIN `anyTypeArrayField` END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",

            withAttributeKeys = { it.toStr() },
            transformation = { it.toNumber() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within expression with condition`() {
        val range = someAnyTypeArrayField()
        val expected = DopeQuery(
            "OBJECT TOSTRING(`iterator1`):(TONUMBER(`iterator1`) + 1) FOR `iterator1` WITHIN `anyTypeArrayField` WHEN TRUE END",
        )
        val underTest = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,

            transformation = { it.toNumber().add(1) },
            withAttributeKeys = { it.toStr() },
            condition = { someBooleanExpression() },
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support object for within expression extension`() {
        val range = someAnyTypeArrayField()
        val expected = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",

            withAttributeKeys = { it.toStr() },
            transformation = { it.toNumber().add(1) },
        )

        val actual = range.mapUnnested(iteratorName = "it") {
            it.toNumber().add(1)
        }.toObject {
            it.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support object for within expression extension with condition`() {
        val range = someAnyTypeArrayField()
        val expected = ObjectRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",

            withAttributeKeys = { it.toStr() },
            transformation = { it.toNumber().add(1) },
            condition = { 1.isLessOrEqualThan(2) },
        )

        val actual = range.filterUnnested(iteratorName = "it") {
            1.isLessOrEqualThan(2)
        }.map {
            it.toNumber().add(1)
        }.toObject {
            it.toStr()
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
