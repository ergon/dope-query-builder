package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DecodeExpressionTest : ParameterDependentTest {
    @Test
    fun `should support decode expression`() {
        val expected = DopeQuery(
            "DECODE(`stringField`, \"someString\", 1, 0)",
            emptyMap(),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someString().toDopeType().resultsIn(someNumber(1).toDopeType()),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "DECODE($1, \"someString\", 1, 0)",
            mapOf("$1" to parameterValue),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someString().toDopeType().resultsIn(someNumber(1).toDopeType()),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "DECODE(`stringField`, \"someString\", 1, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someString().toDopeType().resultsIn(someNumber(1).toDopeType()),
            default = parameterValue.asParameter(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "DECODE($1, \"someString\", 1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someString().toDopeType().resultsIn(someNumber(1).toDopeType()),
            default = parameterValue2.asParameter(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode extension function`() {
        val expression = someStringField()
        val searchResultExpression = someString().resultsIn(someNumber())
        val expected = DecodeExpression(expression, searchResultExpression)

        val actual = decode(expression, searchResultExpression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support decode extension function with default`() {
        val expression = someStringField()
        val searchResultExpression = someString().resultsIn(someNumber())
        val default = someNumber().toDopeType()
        val expected = DecodeExpression(expression, searchResultExpression, default = default)

        val actual = decode(expression, searchResultExpression, default = default)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
