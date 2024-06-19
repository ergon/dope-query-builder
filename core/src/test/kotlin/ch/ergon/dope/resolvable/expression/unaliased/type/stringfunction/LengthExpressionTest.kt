package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LengthExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support length`() {
        val expected = DopeQuery(
            "LENGTH(`stringField`)",
            emptyMap(),
        )

        val actual = LengthExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LENGTH($1)",
            mapOf("$1" to parameterValue),
        )

        val actual = LengthExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
