package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class AdditionExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support addition`() {
        val expected = DopeQuery(
            "(`numberField` + `numberField`)",
            emptyMap(),
        )
        val underTest = AdditionExpression(CMNumberField(), CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 + `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AdditionExpression(parameterValue.asParameter(), CMNumberField())

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition with second parameters`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "(`numberField` + $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AdditionExpression(CMNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support addition function type type`() {
        val left = CMNumberField()
        val right = CMNumberField()
        val expected = AdditionExpression(left, right)

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support addition function type number`() {
        val left = CMNumberField()
        val right = someNumber()
        val expected = AdditionExpression(left, right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support addition function number type`() {
        val left = someNumber()
        val right = CMNumberField()
        val expected = AdditionExpression(left.toDopeType(), right)

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support addition function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
