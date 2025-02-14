package ch.ergon.dope.resolvable.expression.single.type.function.typefunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.type.ToBooleanExpression
import ch.ergon.dope.resolvable.expression.single.type.function.type.toBool
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToBooleanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to boolean expression with no parameters`() {
        val expected = DopeQuery(
            queryString = "TOBOOLEAN(`stringField`)",
        )
        val underTest = ToBooleanExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "TOBOOLEAN($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ToBooleanExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "TOBOOLEAN(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ToBooleanExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean extension`() {
        val string = someStringField()
        val expected = ToBooleanExpression(string)

        val actual = string.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to boolean extension with string`() {
        val string = someString()
        val expected = ToBooleanExpression(string.toDopeType())

        val actual = string.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to boolean extension with number`() {
        val number = someNumber()
        val expected = ToBooleanExpression(number.toDopeType())

        val actual = number.toBool()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
