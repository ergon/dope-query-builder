package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AdditionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support addition`() {
        val expected = DopeQuery(
            "(`numberField` + `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = AdditionExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with positional parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 + `numberField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with named parameter`() {
        val parameterValue = 4
        val parameterName = "param"
        val expected = DopeQuery(
            "(\$$parameterName + `numberField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with positional all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 + $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with named all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "(\$$parameterName1 + \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with positional second parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "(`numberField` + $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = AdditionExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with named second parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            "(`numberField` + \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = AdditionExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with mixed parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val parameterName = "param1"
        val expected = DopeQuery(
            "(\$$parameterName + $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = AdditionExpression(left, right)

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support addition function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = AdditionExpression(left, right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support addition function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = AdditionExpression(left.toDopeType(), right)

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support addition function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
