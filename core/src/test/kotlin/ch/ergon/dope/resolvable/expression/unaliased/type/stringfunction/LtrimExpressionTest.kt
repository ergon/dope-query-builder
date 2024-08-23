package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

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
            manager,
        )
        val underTest = LtrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LTRIM($1, `stringField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = LtrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = DopeQuery(
            "LTRIM($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = LtrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ltrim function type type`() {
        val inStr = someStringField("inStr")
        val extra = someStringField("extra")
        val expected = LtrimExpression(inStr, extra)

        val actual = ltrim(inStr, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
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
