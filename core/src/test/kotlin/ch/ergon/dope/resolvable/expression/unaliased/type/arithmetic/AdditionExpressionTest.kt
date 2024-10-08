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
        )
        val underTest = AdditionExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 + `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 + $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with second parameters`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "(`numberField` + $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AdditionExpression(someNumberField(), parameterValue.asParameter())

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
