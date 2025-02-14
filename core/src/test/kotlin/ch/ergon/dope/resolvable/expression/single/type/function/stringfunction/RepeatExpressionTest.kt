package ch.ergon.dope.resolvable.expression.single.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.string.RepeatExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RepeatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support repeat`() {
        val expected = DopeQuery(
            queryString = "REPEAT(`stringField`, `numberField`)",
        )
        val underTest = RepeatExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "REPEAT($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            queryString = "REPEAT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param1"
        val expected = DopeQuery(
            queryString = "REPEAT(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "REPEAT(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            queryString = "REPEAT(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat function type type`() {
        val inStr = someStringField("inStr")
        val repeatAmount = someNumberField("repeatAmount")
        val expected = RepeatExpression(inStr, repeatAmount)

        val actual = repeat(inStr, repeatAmount)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support repeat function type number`() {
        val inStr = someStringField("inStr")
        val repeatAmount = someNumber()
        val expected = RepeatExpression(inStr, repeatAmount.toDopeType())

        val actual = repeat(inStr, repeatAmount)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support repeat function string type`() {
        val inStr = someString("inStr")
        val repeatAmount = someNumberField("repeatAmount")
        val expected = RepeatExpression(inStr.toDopeType(), repeatAmount)

        val actual = repeat(inStr, repeatAmount)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support repeat function string number`() {
        val inStr = someString("inStr")
        val repeatAmount = someNumber()
        val expected = RepeatExpression(inStr.toDopeType(), repeatAmount.toDopeType())

        val actual = repeat(inStr, repeatAmount)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
