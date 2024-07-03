package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LowerExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support lower`() {
        val expected = DopeQuery(
            "LOWER(`stringField`)",
            emptyMap(),
        )
        val underTest = LowerExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lower with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LOWER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LowerExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
