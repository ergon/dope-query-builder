package ch.ergon.dope.resolvable.expression.type.function.type

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

class ToArrayExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to array expression with no parameters`() {
        val expected = DopeQuery(
            queryString = "TOARRAY(`stringField`)",
        )
        val underTest = ToArrayExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to array expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "TOARRAY($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ToArrayExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to array expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "TOARRAY(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ToArrayExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to array extension`() {
        val string = someString().toDopeType()
        val expected = ToArrayExpression(string)

        val actual = string.toArray()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
