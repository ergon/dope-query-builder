package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RpadExpressionTest : ParameterDependentTest {
    @Test
    fun `should support rpad`() {
        val expected = DopeQuery(
            "RPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = RpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "RPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "RPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra `() {
        val expected = DopeQuery(
            "RPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = RpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "RPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "RPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = RpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rpad function type type type`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = RpadExpression(inStr, size, prefix)

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function type type string`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = RpadExpression(inStr, size, prefix.toDopeType())

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function type number type`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = RpadExpression(inStr, size.toDopeType(), prefix)

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string type type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = RpadExpression(inStr.toDopeType(), size, prefix)

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function type number`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = null
        val expected = RpadExpression(inStr, size.toDopeType(), prefix)

        val actual = rpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function type number string`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = RpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = null
        val expected = RpadExpression(inStr.toDopeType(), size, prefix)

        val actual = rpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string type string`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = RpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string number type`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = RpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string number`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = null
        val expected = RpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = rpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support rpad function string number string`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = RpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

        val actual = rpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
