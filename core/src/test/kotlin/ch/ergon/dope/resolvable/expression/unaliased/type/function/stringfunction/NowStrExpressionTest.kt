package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support now str`() {
        val expected = DopeQuery(
            "NOW_STR()",
            emptyMap(),
            emptyList(),
        )
        val underTest = NowStrExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with no parameters`() {
        val expected = DopeQuery(
            "NOW_STR(`stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = NowStrExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "NOW_STR($1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = NowStrExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            "NOW_STR(\$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = NowStrExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str function type`() {
        val inStr = someStringField("inStr")
        val expected = NowStrExpression(inStr)

        val actual = nowStr(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support now str function string`() {
        val inStr = someString()
        val expected = NowStrExpression(inStr.toDopeType())

        val actual = nowStr(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
