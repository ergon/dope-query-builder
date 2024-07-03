package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SubstringExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support sub string`() {
        val expected = DopeQuery(
            "SUBSTR(`stringField`, 3, 1)",
            emptyMap(),
        )
        val underTest = SubstringExpression(someStringField(), 3, 1)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub string with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "SUBSTR($1, 3, 1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SubstringExpression(parameterValue.asParameter(), 3, 1)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
