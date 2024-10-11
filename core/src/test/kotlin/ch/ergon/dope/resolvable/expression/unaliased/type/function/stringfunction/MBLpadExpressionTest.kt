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

class MBLpadExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mbLpad`() {
        val expected = DopeQuery(
            "MB_LPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = MBLpadExpression(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MB_LPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = MBLpadExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "MB_LPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = MBLpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad with extra `() {
        val expected = DopeQuery(
            "MB_LPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = MBLpadExpression(someStringField(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MB_LPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = MBLpadExpression(parameterValue.asParameter(), someNumberField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "MB_LPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = MBLpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mbLpad function type type type`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBLpadExpression(inStr, size, prefix)

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function type type`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = null
        val expected = MBLpadExpression(inStr, size, prefix)

        val actual = mbLpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function type type string`() {
        val inStr = someStringField("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBLpadExpression(inStr, size, prefix.toDopeType())

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function type number type`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBLpadExpression(inStr, size.toDopeType(), prefix)

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string type type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someStringField("prefix")
        val expected = MBLpadExpression(inStr.toDopeType(), size, prefix)

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function type number`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBLpadExpression(inStr, size.toDopeType(), prefix)

        val actual = mbLpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function type number string`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBLpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string type`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = null
        val expected = MBLpadExpression(inStr.toDopeType(), size, prefix)

        val actual = mbLpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string type string`() {
        val inStr = someString("inStr")
        val size = someNumberField("size")
        val prefix = someString("prefix")
        val expected = MBLpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string number type`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = MBLpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string number`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = null
        val expected = MBLpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = mbLpad(inStr, size)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mbLpad function string number string`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = MBLpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

        val actual = mbLpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
