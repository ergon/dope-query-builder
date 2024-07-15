package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AndExpressionTest : ParameterDependentTest {
    @Test
    fun `should support and`() {
        val expected = DopeQuery(
            "(`booleanField` AND `booleanField`)",
            emptyMap(),
        )
        val underTest = AndExpression(someBooleanField(), someBooleanField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "($1 AND `booleanField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AndExpression(parameterValue.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with all parameter`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = DopeQuery(
            "($1 AND $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = AndExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and with second parameter`() {
        val parameterValue = false
        val expected = DopeQuery(
            "(`booleanField` AND $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = AndExpression(someBooleanField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support and function type type`() {
        val left = someBooleanField()
        val right = someBooleanField()
        val expected = AndExpression(left, right)

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support and function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = AndExpression(left, right.toDopeType())

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support and function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = AndExpression(left.toDopeType(), right)

        val actual = left.and(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
