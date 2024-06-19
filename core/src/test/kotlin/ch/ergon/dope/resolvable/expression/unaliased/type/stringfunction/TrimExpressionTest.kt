package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TrimExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support trim`() {
        val expected = DopeQuery(
            "TRIM(`stringField`)",
            emptyMap(),
        )

        val actual = TrimExpression(someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "TRIM($1)",
            mapOf("$1" to parameterValue),
        )

        val actual = TrimExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra`() {
        val expected = DopeQuery(
            "TRIM(`stringField`, `stringField`)",
            emptyMap(),
        )

        val actual = TrimExpression(someStringField(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra and parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "TRIM($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )

        val actual = TrimExpression(parameterValue.asParameter(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test2"
        val expected = DopeQuery(
            "TRIM($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = TrimExpression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
