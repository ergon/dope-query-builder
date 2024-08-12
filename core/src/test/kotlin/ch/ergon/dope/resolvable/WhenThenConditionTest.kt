package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class WhenThenConditionTest : ParameterDependentTest {
    @Test
    fun `should support when then condition`() {
        val expected = DopeQuery(
            "WHEN `booleanField` THEN `numberField`",
            emptyMap(),
        )
        val underTest = WhenThenCondition(someBooleanField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support when then condition with parameter`() {
        val value = someBoolean()
        val expected = DopeQuery(
            "WHEN $1 THEN `numberField`",
            mapOf("$1" to value),
        )
        val underTest = WhenThenCondition(value.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support when then condition with second parameter`() {
        val value2 = someNumber()
        val expected = DopeQuery(
            "WHEN `booleanField` THEN $1",
            mapOf("$1" to value2),
        )
        val underTest = WhenThenCondition(someBooleanField(), value2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support when then condition with all parameter`() {
        val value = someBoolean()
        val value2 = 3
        val expected = DopeQuery(
            "WHEN $1 THEN $2",
            mapOf("$1" to value, "$2" to value2),
        )
        val underTest = WhenThenCondition(value.asParameter(), value2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support when then condition function`() {
        val condition = someBooleanField()
        val expression = someNumberField()
        val expected = WhenThenCondition(condition, expression)

        val actual = whenThen(condition, expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
