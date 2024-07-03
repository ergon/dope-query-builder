package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class InitCapExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support init cap`() {
        val expected = DopeQuery(
            "INITCAP(`stringField`)",
            emptyMap(),
        )
        val underTest = InitCapExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "INITCAP($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = InitCapExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
