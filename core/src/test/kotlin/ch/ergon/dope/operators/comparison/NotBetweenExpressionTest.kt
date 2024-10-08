package ch.ergon.dope.operators.comparison

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.notBetween
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotBetweenExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOT BETWEEN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` NOT BETWEEN 1 AND 10",
            emptyMap(),
        )
        val underTest = NotBetweenExpression(someNumberField(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN 1 AND 10",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN $2 AND 10",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = NotBetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOT BETWEEN expression with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val expected = DopeQuery(
            queryString = "$1 NOT BETWEEN $2 AND $3",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
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
