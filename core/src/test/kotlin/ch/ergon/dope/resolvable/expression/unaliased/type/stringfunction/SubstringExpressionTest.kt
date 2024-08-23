package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SubstringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support sub string`() {
        val expected = DopeQuery(
            "SUBSTR(`stringField`, 3, 1)",
            emptyMap(),
            manager,
        )
        val underTest = SubstringExpression(someStringField(), 3, 1)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sub string with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "SUBSTR($1, 3, 1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = SubstringExpression(parameterValue.asParameter(), 3, 1)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support substring function type int int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val length = 2
        val expected = SubstringExpression(inStr, startPos, length)

        val actual = substr(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string int int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = 2
        val expected = SubstringExpression(inStr.toDopeType(), startPos, length)

        val actual = substr(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = inStr.length
        val expected = SubstringExpression(inStr.toDopeType(), startPos, length)

        val actual = substr(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
