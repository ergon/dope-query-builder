package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class RpadExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support rpad`() {
        val expected = DopeQuery(
            "RPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = RpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "RPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "RPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra `() {
        val expected = DopeQuery(
            "RPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = RpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "RPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "RPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
