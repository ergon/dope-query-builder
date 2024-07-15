package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterOrEqualThanExpressionTest : ParameterDependentTest {
    @Test
    fun `should support greater or equals`() {
        val expected = DopeQuery(
            "`numberField` >= `numberField`",
            emptyMap(),
        )
        val underTest = GreaterOrEqualThanExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 >= `numberField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 >= $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`numberField` >= $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = GreaterOrEqualThanExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = GreaterOrEqualThanExpression(left, right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = GreaterOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = GreaterOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater or equals function string string`() {
        val left = someString()
        val right = someString()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
