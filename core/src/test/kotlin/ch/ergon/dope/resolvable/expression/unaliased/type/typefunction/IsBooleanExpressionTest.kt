package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanExpression
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IsBooleanExpressionTest : ParameterDependentTest {
    @Test
    fun `should support is boolean expression`() {
        val expected = DopeQuery(
            "ISBOOLEAN(`booleanField`)",
            emptyMap(),
        )
        val underTest = IsBooleanExpression(someBooleanField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean expression with parameter`() {
        val parameterValue = someBoolean()
        val expected = DopeQuery(
            "ISBOOLEAN($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IsBooleanExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is boolean extension`() {
        val boolean = someBooleanExpression()
        val expected = IsBooleanExpression(boolean)

        val actual = boolean.isBoolean()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}