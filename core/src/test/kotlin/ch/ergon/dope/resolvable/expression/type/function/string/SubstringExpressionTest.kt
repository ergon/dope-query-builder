package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SubstringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support sub string`() {
        val expected = DopeQuery(
            queryString = "SUBSTR(`stringField`, 3, 1)",
        )
        val underTest = SubstringExpression(someStringField(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support substring with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "SUBSTR($1, 3, 1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SubstringExpression(parameterValue.asParameter(), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support substring with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SUBSTR(\$$parameterName, 3, 1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SubstringExpression(parameterValue.asParameter(parameterName), 3.toDopeType(), 1.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support substring function type type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = SubstringExpression(inStr, startPos, length)

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function type type`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val expected = SubstringExpression(inStr, startPos)

        val actual = substring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring1 function type type int`() {
        val inStr = someStringField("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = SubstringExpression(inStr, startPos, length.toDopeType())

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function type int int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val length = 2
        val expected = SubstringExpression(inStr, startPos.toDopeType(), length.toDopeType())

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function type int`() {
        val inStr = someStringField("inStr")
        val startPos = 1
        val expected = SubstringExpression(inStr, startPos.toDopeType())

        val actual = substring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string type type`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = SubstringExpression(inStr.toDopeType(), startPos, length)

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string type int`() {
        val inStr = someString("inStr")
        val startPos = someNumberField()
        val length = 1
        val expected = SubstringExpression(inStr.toDopeType(), startPos, length.toDopeType())

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string int int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val length = 2
        val expected = SubstringExpression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = substring(inStr, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support substring function string int`() {
        val inStr = someString("inStr")
        val startPos = 1
        val expected = SubstringExpression(inStr.toDopeType(), startPos.toDopeType())

        val actual = substring(inStr, startPos)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
