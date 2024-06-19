package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class NowStrExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support now str`() {
        val expected = DopeQuery(
            "NOW_STR()",
            emptyMap(),
        )

        val actual = NowStrExpression().toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with format`() {
        val expected = DopeQuery(
            "NOW_STR(`stringField`)",
            emptyMap(),
        )

        val actual = NowStrExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "NOW_STR($1)",
            mapOf("$1" to parameterValue),
        )

        val actual = NowStrExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
