package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.factory.CustomTokenOptions
import kotlin.test.Test
import kotlin.test.assertEquals

class TokensExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support tokens`() {
        val expected = DopeQuery(
            "TOKENS([\"test, test2\"], {\"name\": false, \"specials\": false})",
            emptyMap(),
            emptyList(),
        )
        val underTest = TokensExpression(listOf("test", "test2"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens function`() {
        val inStr = listOf("test1", "test2")
        val expected = TokensExpression(inStr)

        val actual = tokens(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support tokens function with options`() {
        val inStr = listOf("test1", "test2")
        val opt = CustomTokenOptions(name = true, specials = true)
        val expected = TokensExpression(inStr, opt)

        val actual = tokens(inStr, opt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
