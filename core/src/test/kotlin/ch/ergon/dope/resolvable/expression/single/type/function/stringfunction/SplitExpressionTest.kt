package ch.ergon.dope.resolvable.expression.single.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.string.SplitExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.split
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SplitExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support split`() {
        val expected = DopeQuery(
            queryString = "SPLIT(`stringField`)",
        )
        val underTest = SplitExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "SPLIT($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring`() {
        val expected = DopeQuery(
            queryString = "SPLIT(`stringField`, `stringField`)",
        )
        val underTest = SplitExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring and positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "SPLIT($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "SPLIT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SPLIT(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split function type type`() {
        val inStr = someStringField("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr, inSubstring)

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support split function type string`() {
        val inStr = someStringField("inStr")
        val inSubstring = someString("inSubstring")
        val expected = SplitExpression(inStr, inSubstring.toDopeType())

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support split function type`() {
        val inStr = someStringField("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr, inSubstring)

        val actual = split(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support split function string type`() {
        val inStr = someString("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support split function string`() {
        val inStr = someString("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = split(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support split function string string`() {
        val inStr = someString("inStr")
        val inSubstring = someString("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring.toDopeType())

        val actual = split(inStr, inSubstring)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
