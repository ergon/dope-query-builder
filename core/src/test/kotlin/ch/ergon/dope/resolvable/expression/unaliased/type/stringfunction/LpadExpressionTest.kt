package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class LpadExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support lpad`() {
        val expected = DopeQuery(
            "LPAD(`stringField`, `numberField`)",
            emptyMap(),
        )
        val underTest = LpadExpression(someStringField(), CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LPAD($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val expected = DopeQuery(
            "LPAD($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra `() {
        val expected = DopeQuery(
            "LPAD(`stringField`, `numberField`, `stringField`)",
            emptyMap(),
        )
        val underTest = LpadExpression(someStringField(), CMNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra and with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LPAD($1, `numberField`, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), CMNumberField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad with extra and with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = 5
        val parameterValue3 = "extra"
        val expected = DopeQuery(
            "LPAD($1, $2, $3)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = LpadExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lpad function type type type`() {
        val inStr = someStringField("inStr")
        val size = CMNumberField("size")
        val prefix = someStringField("prefix")
        val expected = LpadExpression(inStr, size, prefix)

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function type type string`() {
        val inStr = someStringField("inStr")
        val size = CMNumberField("size")
        val prefix = someString("prefix")
        val expected = LpadExpression(inStr, size, prefix.toDopeType())

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function type number type`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = LpadExpression(inStr, size.toDopeType(), prefix)

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string type type`() {
        val inStr = someString("inStr")
        val size = CMNumberField("size")
        val prefix = someStringField("prefix")
        val expected = LpadExpression(inStr.toDopeType(), size, prefix)

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function type number`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = null
        val expected = LpadExpression(inStr, size.toDopeType(), prefix)

        val actual = lpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function type number string`() {
        val inStr = someStringField("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = LpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string type`() {
        val inStr = someString("inStr")
        val size = CMNumberField("size")
        val prefix = null
        val expected = LpadExpression(inStr.toDopeType(), size, prefix)

        val actual = lpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string type string`() {
        val inStr = someString("inStr")
        val size = CMNumberField("size")
        val prefix = someString("prefix")
        val expected = LpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string number type`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someStringField("prefix")
        val expected = LpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string number`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = null
        val expected = LpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

        val actual = lpad(inStr, size)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support lpad function string number string`() {
        val inStr = someString("inStr")
        val size = someNumber()
        val prefix = someString("prefix")
        val expected = LpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

        val actual = lpad(inStr, size, prefix)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
