package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class PositionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support position`() {
        val expected = DopeQuery(
            "POSITION(`stringField`, `stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = PositionExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "POSITION($1, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
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
            "POSITION($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = PositionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with named parameter`() {
        val parameterValue = "test"
        val parameterName1 = "param1"
        val expected = DopeQuery(
            "POSITION(\$$parameterName1, `stringField`)",
            mapOf(parameterName1 to parameterValue),
            emptyList(),
        )
        val underTest = PositionExpression(parameterValue.asParameter(parameterName1), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "POSITION(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = PositionExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support position with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "POSITION(\$$parameterName, $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
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
