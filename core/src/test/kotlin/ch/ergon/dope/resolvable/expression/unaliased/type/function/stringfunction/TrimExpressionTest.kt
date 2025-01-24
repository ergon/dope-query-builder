package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TrimExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support trim`() {
        val expected = DopeQuery(
            queryString = "TRIM(`stringField`)",
        )
        val underTest = TrimExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "TRIM($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra positional parameters`() {
        val expected = DopeQuery(
            queryString = "TRIM(`stringField`, `stringField`)",
        )
        val underTest = TrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra positional parameter and named parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "TRIM($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test2"
        val expected = DopeQuery(
            queryString = "TRIM($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "TRIM(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim function type type`() {
        val inStr = someStringField("inStr")
        val char = someStringField("extra")
        val expected = TrimExpression(inStr, char)

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support trim function type`() {
        val inStr = someStringField("inStr")
        val char = null
        val expected = TrimExpression(inStr, char)

        val actual = trim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support trim function type string`() {
        val inStr = someStringField("inStr")
        val char = someString()
        val expected = TrimExpression(inStr, char.toDopeType())

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support trim function string type`() {
        val inStr = someString("inStr")
        val char = someStringField("extra")
        val expected = TrimExpression(inStr.toDopeType(), char)

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support trim function string`() {
        val inStr = someString("inStr")
        val char = null
        val expected = TrimExpression(inStr.toDopeType(), char)

        val actual = trim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support trim function string string`() {
        val inStr = someString("inStr")
        val char = someString("extra")
        val expected = TrimExpression(inStr.toDopeType(), char.toDopeType())

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
