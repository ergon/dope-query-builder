package ch.ergon.dope.resolvable.expression.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstring1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.mbSubstring1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBSubstring1ExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mb sub string 1`() {
        val expected = DopeQuery(
            queryString = "MB_SUBSTR1(`stringField`, 3, 1)",
        )
        val underTest = MBSubstring1Expression(someStringField(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mb sub string 1 with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "MB_SUBSTR1($1, 3, 1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = MBSubstring1Expression(parameterValue.asParameter(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbSubstring1 function type type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(inStr, startPos, length)

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val expected = MBSubstring1Expression(inStr, startPos)

        val actual = mbSubstring1(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function type type int`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = MBSubstring1Expression(inStr, startPos, length.toDopeType())

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function type int int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val length = 2
        val expected = MBSubstring1Expression(inStr, startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function type int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val expected = MBSubstring1Expression(inStr, startPos.toDopeType())

        val actual = mbSubstring1(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function string type type`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(inStr.toDopeType(), startPos, length)

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function string type int`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = MBSubstring1Expression(inStr.toDopeType(), startPos, length.toDopeType())

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function string int int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = 2
        val expected = MBSubstring1Expression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbSubstring1 function string int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val expected = MBSubstring1Expression(inStr.toDopeType(), startPos.toDopeType())

        val actual = mbSubstring1(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
