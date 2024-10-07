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

class MBRpadExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mbRpad`() {
        val expected = DopeQuery(
            "MB_RPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = MBRpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MB_RPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "MB_RPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra `() {
        val expected = DopeQuery(
            "MB_RPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = MBRpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MB_RPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "MB_RPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = MBRpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbRpad function type type type`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr, size, prefix)

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function type type string`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr, size, prefix.toDopeType())

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function type number type`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix)

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string type type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix)

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function type number`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix)

        val actual = mbRpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function type number string`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = null
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix)

        val actual = mbRpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string type string`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string number type`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string number`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = mbRpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbRpad function string number string`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBRpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

        val actual = mbRpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
