package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
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
        val underTest = ModuloExpression(someNumberField(), someNumberField())

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
        val underTest = ModuloExpression(parameterValue.asParameter(), someNumberField())

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
}
