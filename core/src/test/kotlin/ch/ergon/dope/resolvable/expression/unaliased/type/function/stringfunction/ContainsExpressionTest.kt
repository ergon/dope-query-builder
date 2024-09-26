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

class ContainsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support contains`() {
        val expected = DopeQuery(
            "CONTAINS(`stringField`, `stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = ContainsExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "CONTAINS($1, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = ContainsExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONTAINS($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = ContainsExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            "CONTAINS(\$$parameterName, `stringField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = ContainsExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with all named parameters`() {
        val parameterValue = "test"
        val parameterName1 = "param1"
        val parameterValue2 = "test"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "CONTAINS(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = ContainsExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "CONTAINS(\$$parameterName, $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
        )
        val underTest = ContainsExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains function type type`() {
        val inStr = someStringField("inStr")
        val searchStr = someStringField("searchStr")
        val expected = ContainsExpression(inStr, searchStr)

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function type string`() {
        val inStr = someStringField("inStr")
        val searchStr = someString()
        val expected = ContainsExpression(inStr, searchStr.toDopeType())

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function string type`() {
        val inStr = someString()
        val searchStr = someStringField("searchStr")
        val expected = ContainsExpression(inStr.toDopeType(), searchStr)

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support contains function string string`() {
        val inStr = someString()
        val searchStr = someString()
        val expected = ContainsExpression(inStr.toDopeType(), searchStr.toDopeType())

        val actual = contains(inStr, searchStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
