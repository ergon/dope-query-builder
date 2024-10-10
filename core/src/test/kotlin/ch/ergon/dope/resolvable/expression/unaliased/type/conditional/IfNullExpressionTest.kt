package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.IfNullExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifNull
import kotlin.test.Test
import kotlin.test.assertEquals

class IfNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if null`() {
        val expected = DopeQuery(
            "IFNULL(`stringField`, `stringField`)",
        )
        val underTest = IfNullExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFNULL($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "IFNULL(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFNULL(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfNullExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "IFNULL(`stringField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfNullExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "IFNULL($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "IFNULL(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfNullExpression(firstExpression, secondExpression)

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
