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

class LtrimExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ltrim`() {
        val expected = DopeQuery(
            "LTRIM(`stringField`, `stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = LtrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LTRIM($1, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = LtrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "LTRIM($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = LtrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            "LTRIM(\$$parameterName, `stringField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = LtrimExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with all named parameters`() {
        val parameterValue = "test"
        val parameterName1 = "param1"
        val parameterValue2 = "test"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "LTRIM(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = LtrimExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "LTRIM(\$$parameterName, $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
        )
        val underTest = LtrimExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim function type string`() {
        val inStr = someStringField("inStr")
        val extra = someString("extra")
        val expected = LtrimExpression(inStr, extra.toDopeType())

        val actual = ltrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ltrim function string type`() {
        val inStr = someString("inStr")
        val extra = someStringField("extra")
        val expected = LtrimExpression(inStr.toDopeType(), extra)

        val actual = ltrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ltrim function string string`() {
        val inStr = someString("inStr")
        val extra = someString("extra")
        val expected = LtrimExpression(inStr.toDopeType(), extra.toDopeType())

        val actual = ltrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ltrim function type`() {
        val inStr = someStringField("inStr")
        val extra = null
        val expected = LtrimExpression(inStr, extra)

        val actual = ltrim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ltrim function string`() {
        val inStr = someString("inStr")
        val extra = null
        val expected = LtrimExpression(inStr.toDopeType(), extra)

        val actual = ltrim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
