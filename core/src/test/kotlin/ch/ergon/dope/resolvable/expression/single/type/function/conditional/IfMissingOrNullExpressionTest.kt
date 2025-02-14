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

class IfMissingOrNullExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if missing or null`() {
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL(`stringField`, `stringField`)",
        )
        val underTest = IfMissingOrNullExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IfMissingOrNullExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL(`stringField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IfMissingOrNullExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "IFMISSINGORNULL(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = IfMissingOrNullExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing or null function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfMissingOrNullExpression(firstExpression, secondExpression)

        val actual = ifMissingOrNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support coalesce`() {
        val expected = DopeQuery(
            queryString = "COALESCE(`stringField`, `stringField`)",
        )
        val underTest = CoalesceExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "COALESCE($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "COALESCE(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "COALESCE(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = CoalesceExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "COALESCE(`stringField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = CoalesceExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            queryString = "COALESCE($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "COALESCE(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = CoalesceExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support coalesce function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = CoalesceExpression(firstExpression, secondExpression)

        val actual = coalesce(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
