package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

class MaskExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mask`() {
        val expected = DopeQuery(
            "MASK(`stringField`, {\"mask\": \"*\"})",
            emptyMap(),
        )
        val underTest = MaskExpression(someStringField(), mapOf("mask" to "*"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mask with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MASK($1, {\"mask\": \"*\"})",
            mapOf("$1" to parameterValue),
        )
        val underTest = MaskExpression(parameterValue.asParameter(), mapOf("mask" to "*"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mask function type`() {
        val inStr = someStringField("inStr")
        val options = mapOf("something1" to "something2")
        val expected = MaskExpression(inStr, options)

        val actual = mask(inStr, options)

        Assert.assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mask function string`() {
        val inStr = someString("inStr")
        val options = mapOf("something1" to "something2")
        val expected = MaskExpression(inStr.toDopeType(), options)

        val actual = mask(inStr, options)

        Assert.assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
