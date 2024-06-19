package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReverseExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support reverse`() {
        val expected = DopeQuery(
            "REVERSE(`stringField`)",
            emptyMap(),
        )

        val actual = ReverseExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "REVERSE($1)",
            mapOf("$1" to parameterValue),
        )

        val actual = ReverseExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
