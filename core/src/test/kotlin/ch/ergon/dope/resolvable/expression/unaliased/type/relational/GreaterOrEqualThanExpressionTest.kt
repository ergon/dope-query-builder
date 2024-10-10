package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterOrEqualThanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support greater or equals`() {
        val expected = DopeQuery(
            "`numberField` >= `numberField`",
        )
        val underTest = GreaterOrEqualThanExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 >= `numberField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with all positional parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 >= $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with second positional parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`numberField` >= $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = GreaterOrEqualThanExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            "$$parameterName >= `numberField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with all named parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "$$parameterName >= $$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = GreaterOrEqualThanExpression(left, right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = GreaterOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = GreaterOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater or equals function string string`() {
        val left = someString()
        val right = someString()
        val expected = GreaterOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
