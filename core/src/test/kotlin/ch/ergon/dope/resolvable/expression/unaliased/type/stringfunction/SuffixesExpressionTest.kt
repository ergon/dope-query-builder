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

class SuffixesExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support split`() {
        val expected = DopeQuery(
            "SUFFIXES(`stringField`)",
            emptyMap(),
        )
        val underTest = SuffixesExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "SUFFIXES($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SuffixesExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support suffix function type`() {
        val inStr = someStringField("inStr")
        val expected = SuffixesExpression(inStr)

        val actual = suffixes(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support suffix function string`() {
        val inStr = someString()
        val expected = SuffixesExpression(inStr.toDopeType())

        val actual = suffixes(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
