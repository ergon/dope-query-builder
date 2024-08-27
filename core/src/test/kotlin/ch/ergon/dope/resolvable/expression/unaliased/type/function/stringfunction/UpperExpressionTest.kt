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

class UpperExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support upper`() {
        val expected = DopeQuery(
            "UPPER(`stringField`)",
            emptyMap(),
        )
        val underTest = UpperExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support upper with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "UPPER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = UpperExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support upper function type`() {
        val inStr = someStringField("inStr")
        val expected = UpperExpression(inStr)

        val actual = upper(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support upper function string`() {
        val inStr = someString()
        val expected = UpperExpression(inStr.toDopeType())

        val actual = upper(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
