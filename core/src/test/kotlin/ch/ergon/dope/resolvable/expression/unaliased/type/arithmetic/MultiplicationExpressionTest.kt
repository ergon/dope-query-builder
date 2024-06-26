package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiplicationExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support multiplication`() {
        val expected = DopeQuery(
            "(`numberField` * `numberField`)",
            emptyMap(),
        )

        val actual = MultiplicationExpression(someNumberField(), someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplication with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 * `numberField`)",
            mapOf("$1" to parameterValue),
        )

        val actual = MultiplicationExpression(parameterValue.asParameter(), someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplication with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 * $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = MultiplicationExpression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}