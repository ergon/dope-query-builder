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

class NowStringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support now str`() {
        val expected = DopeQuery(
            "NOW_STR()",
            emptyMap(),
        )
        val underTest = NowStringExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with format`() {
        val expected = DopeQuery(
            "NOW_STR(`stringField`)",
            emptyMap(),
        )
        val underTest = NowStringExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "NOW_STR($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = NowStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str function type`() {
        val inStr = someStringField("inStr")
        val expected = NowStringExpression(inStr)

        val actual = nowString(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support now str function string`() {
        val inStr = someString()
        val expected = NowStringExpression(inStr.toDopeType())

        val actual = nowString(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
