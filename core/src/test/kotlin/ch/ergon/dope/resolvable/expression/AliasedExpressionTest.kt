package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class AliasedExpressionTest : ParameterDependentTest {
    @Test
    fun `should support aliased expression`() {
        val expected = DopeQuery(
            "`stringField` AS `test`",
            emptyMap(),
        )
        val underTest = AliasedExpression(someStringField(), "test")

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            "$1 AS `test`",
            mapOf("$1" to parameterValue),
        )
        val underTest = AliasedExpression(parameterValue.asParameter(), "test")

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias function type`() {
        val unaliasedExpression = someStringField()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression, alias)

        val actual = unaliasedExpression.alias(alias)

        kotlin.test.assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support alias function number`() {
        val unaliasedExpression = someNumber()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        kotlin.test.assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support alias function string`() {
        val unaliasedExpression = someString()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        kotlin.test.assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support alias function boolean`() {
        val unaliasedExpression = someBoolean()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        kotlin.test.assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
