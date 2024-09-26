package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNumberExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is number expression with no parameters`() {
        val expected = DopeQuery(
            "ISNUMBER(`numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = IsNumberExpression(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number expression with positional parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "ISNUMBER($1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = IsNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number expression with named parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            "ISNUMBER(\$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = IsNumberExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number extension`() {
        val number = someNumber().toDopeType()
        val expected = IsNumberExpression(number)

        val actual = number.isNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
