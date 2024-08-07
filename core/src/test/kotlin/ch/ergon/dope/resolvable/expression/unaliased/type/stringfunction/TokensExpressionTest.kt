package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.tokens
import kotlin.test.Test
import kotlin.test.assertEquals

class TokensExpressionTest : ParameterDependentTest {
    @Test
    fun `should support tokens`() {
        val expected = DopeQuery(
            "TOKENS([\"test, test2\"], {\"name\": false, \"specials\": false})",
            emptyMap(),
        )
        val underTest = TokensExpression(listOf("test", "test2"))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens function`() {
        val inStr = listOf("test1", "test2")
        val expected = TokensExpression(inStr)

        val actual = tokens(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support tokens function with options`() {
        val inStr = listOf("test1", "test2")
        val opt = CustomTokenOptions(name = true, specials = true)
        val expected = TokensExpression(inStr, opt)

        val actual = tokens(inStr, opt)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
