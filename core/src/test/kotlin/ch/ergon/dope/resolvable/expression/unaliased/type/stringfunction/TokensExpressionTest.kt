package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TokensExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support tokens`() {
        val expected = DopeQuery(
            "TOKENS([\"test, test2\"], {\"name\": false, \"specials\": false})",
            emptyMap(),
        )

        val actual = TokensExpression(listOf("test", "test2")).toDopeQuery()

        assertEquals(expected, actual)
    }
}
