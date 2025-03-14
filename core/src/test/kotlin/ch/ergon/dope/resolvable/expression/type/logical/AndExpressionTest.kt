package ch.ergon.dope.resolvable.expression.type.logical

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AndExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support and`() {
        val expected = DopeQuery(
            queryString = "(`booleanField` AND `booleanField`)",
        )
        val underTest = AndExpression(someBooleanField(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with positional parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            queryString = "($1 AND `booleanField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AndExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all positional parameters`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = DopeQuery(
            queryString = "($1 AND $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = AndExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with second positional parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            queryString = "(`booleanField` AND $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = AndExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with named parameter`() {
        val parameterValue = true
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "($$parameterName AND `booleanField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all named parameters`() {
        val parameterValue = true
        val parameterValue2 = false
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "($$parameterName AND $$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = AndExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and function type type`() {
        val left = someBooleanField()
        val right = someBooleanField()
        val expected = AndExpression(left, right)

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = AndExpression(left, right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support and function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = AndExpression(left.toDopeType(), right)

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
