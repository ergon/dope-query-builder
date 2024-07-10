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

    @Test
    fun `should support split function type type`() {
        val inStr = someStringField("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr, inSubstring)

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support split function type`() {
        val inStr = someStringField("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr, inSubstring)

        val actual = split(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support split function string type`() {
        val inStr = someString("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support split function string`() {
        val inStr = someString("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = split(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support split function string string`() {
        val inStr = someString("inStr")
        val inSubstring = someString("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring.toDopeType())

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
