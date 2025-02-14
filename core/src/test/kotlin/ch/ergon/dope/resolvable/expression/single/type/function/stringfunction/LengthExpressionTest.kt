package ch.ergon.dope.resolvable.expression.single.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.string.LengthExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.length
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LengthExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support length with no parameters`() {
        val expected = DopeQuery(
            queryString = "LENGTH(`stringField`)",
        )
        val underTest = LengthExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "LENGTH($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "LENGTH(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LengthExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length function type`() {
        val inStr = someStringField("inStr")
        val expected = LengthExpression(inStr)

        val actual = length(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support length function string`() {
        val inStr = someString()
        val expected = LengthExpression(inStr.toDopeType())

        val actual = length(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
