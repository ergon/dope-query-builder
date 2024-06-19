package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LtrimExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ltrim`() {
        val expected = DopeQuery(
            "LTRIM(`stringField`, `stringField`)",
            emptyMap(),
        )

        val actual = LtrimExpression(someStringField(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LTRIM($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )

        val actual = LtrimExpression(parameterValue.asParameter(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "LTRIM($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = LtrimExpression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
