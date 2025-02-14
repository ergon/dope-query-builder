package ch.ergon.dope.resolvable.expression.single.type.function.conditional

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if missing`() {
        val expected = DopeQuery(
            queryString = "IFMISSING(`stringField`, `stringField`)",
        )
        val underTest = IfMissingExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "IFMISSING($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "IFMISSING(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "IFMISSING(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfMissingExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "IFMISSING(`stringField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfMissingExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            queryString = "IFMISSING($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "IFMISSING(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfMissingExpression(firstExpression, secondExpression)

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
