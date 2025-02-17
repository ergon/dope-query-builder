package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.aggregate.AliasedAggregateExpression
import ch.ergon.dope.resolvable.expression.aggregate.countAsterisk
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AliasedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support aliased expression`() {
        val expected = DopeQuery(
            queryString = "COUNT(*) AS `count`",
        )
        val underTest = AliasedAggregateExpression(countAsterisk(), "count")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased type expression`() {
        val expected = DopeQuery(
            queryString = "`stringField` AS `test`",
        )
        val underTest = AliasedTypeExpression(someStringField(), "test")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with positional parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            queryString = "$1 AS `test`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest =
            AliasedTypeExpression(parameterValue.asParameter(), "test")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with named parameter`() {
        val parameterValue = "testValue"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$$parameterName AS `test`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = AliasedTypeExpression(
            parameterValue.asParameter(parameterName),
            "test",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias function type`() {
        val typeExpression = someStringField()
        val alias = "alias"
        val expected = AliasedTypeExpression(typeExpression, alias)

        val actual = typeExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function number`() {
        val number = someNumber()
        val alias = "alias"
        val expected = AliasedTypeExpression(number.toDopeType(), alias)

        val actual = number.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function string`() {
        val string = someString()
        val alias = "alias"
        val expected = AliasedTypeExpression(string.toDopeType(), alias)

        val actual = string.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function boolean`() {
        val boolean = someBoolean()
        val alias = "alias"
        val expected = AliasedTypeExpression(boolean.toDopeType(), alias)

        val actual = boolean.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support alias function select expression`() {
        val selectExpression = someSelectClause().asExpression()
        val alias = "alias"
        val expected = AliasedTypeExpression(selectExpression, alias)

        val actual = selectExpression.alias(alias)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
