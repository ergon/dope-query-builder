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

class ReverseExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support reverse`() {
        val expected = DopeQuery(
            "REVERSE(`stringField`)",
            emptyMap(),
        )
        val underTest = ReverseExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "REVERSE($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ReverseExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse function type`() {
        val inStr = someStringField("inStr")
        val expected = ReverseExpression(inStr)

        val actual = reverse(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support reverse function string`() {
        val inStr = someString()
        val expected = ReverseExpression(inStr.toDopeType())

        val actual = reverse(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
