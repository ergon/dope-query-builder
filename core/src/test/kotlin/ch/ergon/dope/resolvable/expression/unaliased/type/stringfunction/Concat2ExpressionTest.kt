package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val underTest = Concat2Expression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "CONCAT2($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = Concat2Expression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery()

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
        val underTest = Concat2Expression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

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
        val underTest = Concat2Expression(parameterValue.asParameter(), someStringField(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support concat2 function type type`() {
        val separator = someStringField("separator")
        val string = someStringField()
        val expected = Concat2Expression(separator, string)

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support concat2 function string type`() {
        val separator = someString("separator")
        val string = someStringField()
        val expected = Concat2Expression(separator.toDopeType(), string)

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support concat2 function type string`() {
        val separator = someStringField("separator")
        val string = someString()
        val expected = Concat2Expression(separator, string.toDopeType())

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support concat2 function string string`() {
        val separator = someString("separator")
        val string = someString()
        val expected = Concat2Expression(separator.toDopeType(), string.toDopeType())

        val actual = concat2(separator, string)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
