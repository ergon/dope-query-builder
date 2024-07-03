package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LpadExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support lpad`() {
        val expected = DopeQuery(
            "LPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = LpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "LPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra `() {
        val expected = DopeQuery(
            "LPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = LpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "LPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
