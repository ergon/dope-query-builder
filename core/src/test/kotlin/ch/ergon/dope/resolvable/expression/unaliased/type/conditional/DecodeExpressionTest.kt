package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSearchNumberResult
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DecodeExpressionTest : ParameterDependentTest {
    @Test
    fun `should support decode expression`() {
        val expected = DopeQuery(
            "DECODE(`stringField`, \"someString\", 5, 0)",
            emptyMap(),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someStringSearchNumberResult(),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "DECODE($1, \"someString\", 5, 0)",
            mapOf("$1" to parameterValue),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someStringSearchNumberResult(),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "DECODE(`stringField`, \"someString\", 5, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someStringSearchNumberResult(),
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
            "DECODE($1, \"someString\", 5, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someStringSearchNumberResult(),
            default = parameterValue2.asParameter(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode extension function`() {
        val expression = someStringField()
        val searchResult = someStringSearchNumberResult()
        val expected = DecodeExpression(expression, searchResult)

        val actual = decode(expression, searchResult)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support decode extension function with default`() {
        val expression = someStringField()
        val searchResult = someStringSearchNumberResult()
        val default = someNumber().toDopeType()
        val expected = DecodeExpression(expression, searchResult, default = default)

        val actual = decode(expression, searchResult, default = default)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
