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

class PositionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support position`() {
        val expected = DopeQuery(
            queryString = "POSITION(`stringField`, `stringField`)",
        )
        val underTest = PositionExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "POSITION($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = PositionExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "POSITION($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = PositionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param1"
        val expected = DopeQuery(
            queryString = "POSITION(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = PositionExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "POSITION(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = PositionExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "POSITION(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = PositionExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = PositionExpression(inStr, searchStr)

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString("searchStr")
        val expected = PositionExpression(inStr, searchStr.toDopeType())

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function string type`() {
        val inStr = someString("inStr")
        val searchStr = someStringField("searchStr")
        val expected = PositionExpression(inStr.toDopeType(), searchStr)

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support position function string string`() {
        val inStr = someString("inStr")
        val searchStr = someString("searchStr")
        val expected = PositionExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = position(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
