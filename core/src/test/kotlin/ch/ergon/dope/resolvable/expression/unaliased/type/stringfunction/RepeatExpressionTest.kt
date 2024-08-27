package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.RepeatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.repeat
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
        )
        val underTest = RepeatExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "REPEAT($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support repeat with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "REPEAT($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = RepeatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

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
