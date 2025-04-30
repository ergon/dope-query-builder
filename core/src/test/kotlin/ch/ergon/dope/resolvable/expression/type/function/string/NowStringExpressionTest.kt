package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowStringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support now str`() {
        val expected = DopeQuery(
            queryString = "NOW_STR()",

        )
        val underTest = NowStringExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with no parameters`() {
        val expected = DopeQuery(
            queryString = "NOW_STR(`stringField`)",

        )
        val underTest = NowStringExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "NOW_STR($1)",

            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NowStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support now str with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "NOW_STR(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),

        )
        val underTest = NowStringExpression(parameterValue.asParameter(parameterName))

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
