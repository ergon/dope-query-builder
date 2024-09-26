package ch.ergon.dope.operators.comparison

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.between
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class BetweenExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support BETWEEN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` BETWEEN 1 AND 10",
            emptyMap(),
            emptyList(),
        )
        val underTest = BetweenExpression(someNumberField(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$$parameterName BETWEEN 1 AND 10",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(parameterName), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            queryString = "$1 BETWEEN 1 AND 10",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with named first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "\$$parameterName1 BETWEEN \$$parameterName2 AND 10",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = BetweenExpression(
            parameterValue.asParameter(parameterName1),
            parameterValue2.asParameter(parameterName2),
            10.toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with positional first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "$1 BETWEEN $2 AND 10",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with named all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val parameterName3 = "param3"
        val expected = DopeQuery(
            queryString = "\$$parameterName1 BETWEEN \$$parameterName2 AND \$$parameterName3",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            emptyList(),
        )
        val underTest = BetweenExpression(
            parameterValue.asParameter(parameterName1),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with positional all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val expected = DopeQuery(
            queryString = "$1 BETWEEN $2 AND $3",
            emptyMap(),
            listOf(parameterValue, parameterValue2, parameterValue3),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN extension`() {
        val expression = someNumberField()
        val start = 1.toDopeType()
        val end = 10.toDopeType()
        val expected = BetweenExpression(expression, start, end)

        val actual = expression.between(start, end)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
