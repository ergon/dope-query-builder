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
        val underTest = ReverseExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "REVERSE($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ReverseExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse function type`() {
        val inStr = someStringField("inStr")
        val expected = ReverseExpression(inStr)

        val actual = reverse(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support reverse function string`() {
        val inStr = someString()
        val expected = ReverseExpression(inStr.toDopeType())

        val actual = reverse(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
