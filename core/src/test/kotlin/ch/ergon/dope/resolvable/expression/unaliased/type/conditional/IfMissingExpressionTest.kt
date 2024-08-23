package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.IfMissingExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissing
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingExpressionTest : ParameterDependentTest {
    @Test
    fun `should support if missing`() {
        val expected = DopeQuery(
            "IFMISSING(`stringField`, `stringField`)",
            emptyMap(),
        )
        val underTest = IfMissingExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSING($1, `stringField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSING(`stringField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IfMissingExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "IFMISSING($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfMissingExpression(firstExpression, secondExpression)

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
