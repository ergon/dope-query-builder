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

class InitCapExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support init cap`() {
        val expected = DopeQuery(
            queryString = "INITCAP(`stringField`)",
        )
        val underTest = InitCapExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "INITCAP($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = InitCapExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "INITCAP(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = InitCapExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support init cap function type`() {
        val inStr = someStringField("inStr")
        val expected = InitCapExpression(inStr)

        val actual = initCap(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support init cap function string`() {
        val inStr = someString()
        val expected = InitCapExpression(inStr.toDopeType())

        val actual = initCap(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support title function type`() {
        val inStr = someStringField("inStr")
        val expected = TitleExpression(inStr)

        val actual = title(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support title function string`() {
        val inStr = someString()
        val expected = TitleExpression(inStr.toDopeType())

        val actual = title(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
