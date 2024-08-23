package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.IsNumberExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNumberExpressionTest : ParameterDependentTest {
    @Test
    fun `should support is number expression`() {
        val expected = DopeQuery(
            "ISNUMBER(`numberField`)",
            emptyMap(),
        )
        val underTest = IsNumberExpression(someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number expression with parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "ISNUMBER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IsNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number extension`() {
        val number = someNumber().toDopeType()
        val expected = IsNumberExpression(number)

        val actual = number.isNumber()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
