package ch.ergon.dope.operators.comparison

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.between
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BetweenExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support BETWEEN expression`() {
        val expected = DopeQuery(
            queryString = "`numberField` BETWEEN 1 AND 10",
            emptyMap(),
        )
        val underTest = BetweenExpression(someNumberField(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            queryString = "$1 BETWEEN 1 AND 10",
            mapOf("$1" to parameterValue),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), 1.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with first and second parameter`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val expected = DopeQuery(
            queryString = "$1 BETWEEN $2 AND 10",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), 10.toDopeType())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN expression with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 1
        val parameterValue3 = 10
        val expected = DopeQuery(
            queryString = "$1 BETWEEN $2 AND $3",
            mapOf("$1" to parameterValue, "$2" to parameterValue2, "$3" to parameterValue3),
        )
        val underTest = BetweenExpression(parameterValue.asParameter(), parameterValue2.asParameter(), parameterValue3.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BETWEEN extension`() {
        val expression = someNumberField()
        val start = 1.toDopeType()
        val end = 10.toDopeType()
        val expected = BetweenExpression(expression, start, end)

        val actual = expression.between(start, end)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
