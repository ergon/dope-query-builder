package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotBetweenExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOT BETWEEN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` NOT BETWEEN 1 AND 10",
        )
        val underTest = NotBetweenExpression(someNumberField(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "\$$parameterName NOT BETWEEN 1 AND 10",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(parameterName), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN 1 AND 10",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with named first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "\$$parameterName NOT BETWEEN \$$parameterName2 AND 10",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = NotBetweenExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            10.toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with positional first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN $2 AND 10",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with named all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val parameterName = "param1"
        val parameterName2 = "param2"
        val parameterName3 = "param3"
        val expected = DopeQuery(
            queryString = "\$$parameterName NOT BETWEEN \$$parameterName2 AND \$$parameterName3",
            DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            ),
        )
        val underTest = NotBetweenExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with positional all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN $2 AND $3",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2, parameterValue3)),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN extension`() {
        val expression = someNumberField()
        val start = 1.toDopeType()
        val end = 10.toDopeType()
        val expected = NotBetweenExpression(expression, start, end)

        val actual = expression.notBetween(start, end)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
