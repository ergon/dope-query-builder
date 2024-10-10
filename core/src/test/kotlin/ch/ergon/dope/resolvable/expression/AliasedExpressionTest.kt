package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AliasedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support aliased expression`() {
        val expected = DopeQuery(
            "`stringField` AS `test`",
        )
        val underTest = AliasedExpression(someStringField(), "test")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with positional parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            "$1 AS `test`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AliasedExpression(parameterValue.asParameter(), "test")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with named parameter`() {
        val parameterValue = "testValue"
        val parameterName = "param"
        val expected = DopeQuery(
            "\$$parameterName AS `test`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = AliasedExpression(parameterValue.asParameter(parameterName), "test")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias function type`() {
        val unaliasedExpression = someStringField()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression, alias)

        val actual = unaliasedExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function number`() {
        val unaliasedExpression = someNumber()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function string`() {
        val unaliasedExpression = someString()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function boolean`() {
        val unaliasedExpression = someBoolean()
        val alias = "alias"
        val expected = AliasedExpression(unaliasedExpression.toDopeType(), alias)

        val actual = unaliasedExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
