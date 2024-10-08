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

class LowerExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support lower`() {
        val expected = DopeQuery(
            "LOWER(`stringField`)",
            emptyMap(),
        )
        val underTest = LowerExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lower with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "LOWER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = LowerExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lower function type`() {
        val inStr = someStringField("inStr")
        val expected = LowerExpression(inStr)

        val actual = lower(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support lower function string`() {
        val inStr = someString()
        val expected = LowerExpression(inStr.toDopeType())

        val actual = lower(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
