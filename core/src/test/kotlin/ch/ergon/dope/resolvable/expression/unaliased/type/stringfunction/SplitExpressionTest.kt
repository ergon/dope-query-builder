package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SplitExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support split`() {
        val expected = DopeQuery(
            "SPLIT(`stringField`)",
            emptyMap(),
        )
        val underTest = SplitExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "SPLIT($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SplitExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring`() {
        val expected = DopeQuery(
            "SPLIT(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = SplitExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "SPLIT($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "SPLIT($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
