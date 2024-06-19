package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class UpperExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support upper`() {
        val expected = DopeQuery(
            "UPPER(`stringField`)",
            emptyMap(),
        )

        val actual = UpperExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support upper with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "UPPER($1)",
            mapOf("$1" to parameterValue),
        )

        val actual = UpperExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
