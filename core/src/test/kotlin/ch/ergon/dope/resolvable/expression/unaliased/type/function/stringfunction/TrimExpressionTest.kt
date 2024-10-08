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

class TrimExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support trim`() {
        val expected = DopeQuery(
            "TRIM(`stringField`)",
            emptyMap(),
        )
        val underTest = TrimExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "TRIM($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = TrimExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra`() {
        val expected = DopeQuery(
            "TRIM(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = TrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra and parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "TRIM($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test2"
        val expected = DopeQuery(
            "TRIM($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

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
