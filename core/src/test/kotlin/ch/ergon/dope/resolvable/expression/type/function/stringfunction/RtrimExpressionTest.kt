package ch.ergon.dope.resolvable.expression.type.function.stringfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.string.RtrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RtrimExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support rtrim`() {
        val expected = DopeQuery(
            queryString = "RTRIM(`stringField`, `stringField`)",
        )
        val underTest = RtrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "RTRIM($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "RTRIM($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param1"
        val expected = DopeQuery(
            queryString = "RTRIM(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with all named parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "RTRIM(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            queryString = "RTRIM(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim function type type`() {
        val inStr = someStringField("inStr")
        val extra = someStringField("extra")
        val expected = RtrimExpression(inStr, extra)

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rtrim function type string`() {
        val inStr = someStringField("inStr")
        val extra = someString("extra")
        val expected = RtrimExpression(inStr, extra.toDopeType())

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rtrim function string type`() {
        val inStr = someString("inStr")
        val extra = someStringField("extra")
        val expected = RtrimExpression(inStr.toDopeType(), extra)

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rtrim function string string`() {
        val inStr = someString("inStr")
        val extra = someString("extra")
        val expected = RtrimExpression(inStr.toDopeType(), extra.toDopeType())

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rtrim function type`() {
        val inStr = someStringField("inStr")
        val extra = null
        val expected = RtrimExpression(inStr, extra)

        val actual = rtrim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rtrim function string`() {
        val inStr = someString("inStr")
        val extra = null
        val expected = RtrimExpression(inStr.toDopeType(), extra)

        val actual = rtrim(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
