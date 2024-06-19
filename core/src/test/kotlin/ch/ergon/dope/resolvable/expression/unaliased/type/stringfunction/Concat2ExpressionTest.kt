package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class Concat2ExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support concat2`() {
        val expected = DopeQuery(
            "CONCAT2(`stringField`, `stringField`)",
            emptyMap(),
        )

        val actual = Concat2Expression(someStringField(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "CONCAT2($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )

        val actual = Concat2Expression(parameterValue.asParameter(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONCAT2($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = Concat2Expression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with mixed parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONCAT2($1, `stringField`, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = Concat2Expression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
