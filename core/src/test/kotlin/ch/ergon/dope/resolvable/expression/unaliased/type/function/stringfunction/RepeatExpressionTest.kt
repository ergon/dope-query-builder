package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RepeatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support repeat`() {
        val expected = DopeQuery(
            "REPEAT(`stringField`, `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = RepeatExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "REPEAT($1, `numberField`)",
            emptyMap(),
            listOf(parameterValue),
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
            "REPEAT($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with named parameter`() {
        val parameterValue = "test"
        val parameterName1 = "param1"
        val expected = DopeQuery(
            "REPEAT(\$$parameterName1, `numberField`)",
            mapOf(parameterName1 to parameterValue),
            emptyList(),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(parameterName1), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "REPEAT(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "REPEAT(\$$parameterName, $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
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
