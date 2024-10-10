package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class OrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support or`() {
        val expected = DopeQuery(
            queryString = "(`booleanField` OR `booleanField`)",
        )
        val underTest = OrExpression(someBooleanField(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "($1 OR `booleanField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = OrExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with all positional parameters`() {
        val parameterValue = true
        val parameterValue2 = false
        val expected = DopeQuery(
            queryString = "($1 OR $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = OrExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with second positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            queryString = "(`booleanField` OR $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = OrExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "($$parameterName OR `booleanField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = OrExpression(parameterValue.asParameter(parameterName), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with all named parameters`() {
        val parameterValue = true
        val parameterValue2 = false
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "($$parameterName OR $$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = OrExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or function type type`() {
        val left = someBooleanField()
        val right = someBooleanField()
        val expected = OrExpression(left, right)

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = OrExpression(left, right.toDopeType())

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support or function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = OrExpression(left.toDopeType(), right)

        val actual = left.or(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
