package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.RtrimExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.rtrim
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RtrimExpressionTest : ParameterDependentTest {
    @Test
    fun `should support rtrim`() {
        val expected = DopeQuery(
            "RTRIM(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = RtrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "RTRIM($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "RTRIM($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = RtrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rtrim function type type`() {
        val inStr = someStringField("inStr")
        val extra = someStringField("extra")
        val expected = RtrimExpression(inStr, extra)

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rtrim function type string`() {
        val inStr = someStringField("inStr")
        val extra = someString("extra")
        val expected = RtrimExpression(inStr, extra.toDopeType())

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rtrim function string type`() {
        val inStr = someString("inStr")
        val extra = someStringField("extra")
        val expected = RtrimExpression(inStr.toDopeType(), extra)

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rtrim function string string`() {
        val inStr = someString("inStr")
        val extra = someString("extra")
        val expected = RtrimExpression(inStr.toDopeType(), extra.toDopeType())

        val actual = rtrim(inStr, extra)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rtrim function type`() {
        val inStr = someStringField("inStr")
        val extra = null
        val expected = RtrimExpression(inStr, extra)

        val actual = rtrim(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rtrim function string`() {
        val inStr = someString("inStr")
        val extra = null
        val expected = RtrimExpression(inStr.toDopeType(), extra)

        val actual = rtrim(inStr)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
