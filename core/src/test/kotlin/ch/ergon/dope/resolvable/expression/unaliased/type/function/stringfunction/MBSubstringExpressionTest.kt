package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBSubstringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mb sub string`() {
        val expected = DopeQuery(
            queryString = "MB_SUBSTR(`stringField`, 3, 1)",
        )
        val underTest = MBSubstringExpression(someStringField(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mb sub string with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "MB_SUBSTR($1, 3, 1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBSubstringExpression(parameterValue.asParameter(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbSubstring function type type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = MBSubstringExpression(inStr, startPos, length)

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val expected = MBSubstringExpression(inStr, startPos)

        val actual = mbSubstring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function type type int`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = MBSubstringExpression(inStr, startPos, length.toDopeType())

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function type int int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val length = 2
        val expected = MBSubstringExpression(inStr, startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function type int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val expected = MBSubstringExpression(inStr, startPos.toDopeType())

        val actual = mbSubstring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function string type type`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = MBSubstringExpression(inStr.toDopeType(), startPos, length)

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function string type int`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = MBSubstringExpression(inStr.toDopeType(), startPos, length.toDopeType())

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function string int int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = 2
        val expected = MBSubstringExpression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring function string int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val expected = MBSubstringExpression(inStr.toDopeType(), startPos.toDopeType())

        val actual = mbSubstring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
