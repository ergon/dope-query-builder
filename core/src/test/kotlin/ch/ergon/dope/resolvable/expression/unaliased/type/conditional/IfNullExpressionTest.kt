package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class IfNullExpressionTest : ParameterDependentTest {
    @Test
    fun `should support if null`() {
        val expected = DopeQuery(
            "IFNULL(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = IfNullExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFNULL($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFNULL(`stringField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfNullExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "IFNULL($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = IfNullExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if null function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfNullExpression(firstExpression, secondExpression)

        val actual = ifNull(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}