package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowStrExpressionTest : ParameterDependentTest {
    @Test
    fun `should support now str`() {
        val expected = DopeQuery(
            "NOW_STR()",
            emptyMap(),
        )
        val underTest = NowStrExpression()

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with format`() {
        val expected = DopeQuery(
            "NOW_STR(`stringField`)",
            emptyMap(),
        )
        val underTest = NowStrExpression(someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "NOW_STR($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = NowStrExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str function type`() {
        val inStr = someStringField("inStr")
        val expected = NowStrExpression(inStr)

        val actual = nowStr(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support now str function string`() {
        val inStr = someString()
        val expected = NowStrExpression(inStr.toDopeType())

        val actual = nowStr(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
