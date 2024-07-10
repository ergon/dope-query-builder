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

class ModuloExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support modulo`() {
        val expected = DopeQuery(
            "(`numberField` % `numberField`)",
            emptyMap(),
        )
        val underTest = ModuloExpression(CMNumberField(), CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 % `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ModuloExpression(parameterValue.asParameter(), CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 % $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = ModuloExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with second parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "(`numberField` % $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ModuloExpression(CMNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo function type type`() {
        val left = CMNumberField()
        val right = CMNumberField()
        val expected = ModuloExpression(left, right)

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support modulo function type number`() {
        val left = CMNumberField()
        val right = someNumber()
        val expected = ModuloExpression(left, right.toDopeType())

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support modulo function number type`() {
        val left = someNumber()
        val right = CMNumberField()
        val expected = ModuloExpression(left.toDopeType(), right)

        val actual = left.mod(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support modulo function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = AdditionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.add(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
