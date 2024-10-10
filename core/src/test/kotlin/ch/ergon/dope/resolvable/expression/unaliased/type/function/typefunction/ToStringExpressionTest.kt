package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToStringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to string expression with no parameters`() {
        val expected = DopeQuery(
            queryString = "TOSTRING(`stringField`)",
        )
        val underTest = ToStringExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "TOSTRING($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ToStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "TOSTRING(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ToStringExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string extension`() {
        val number = someNumberField()
        val expected = ToStringExpression(number)

        val actual = number.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to string extension with number`() {
        val number = someNumber()
        val expected = ToStringExpression(number.toDopeType())

        val actual = number.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to string extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToStringExpression(boolean.toDopeType())

        val actual = boolean.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
