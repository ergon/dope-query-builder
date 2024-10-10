package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSearchNumberResult
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DecodeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support decode expression`() {
        val expected = DopeQuery(
            queryString = "DECODE(`stringField`, \"someString\", 5, 0)",
        )
        val underTest = DecodeExpression(
            someStringField(),
            someStringSearchNumberResult(),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "DECODE($1, \"someString\", 5, 0)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someStringSearchNumberResult(),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "DECODE(\$$parameterName, \"someString\", 5, 0)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(parameterName),
            someStringSearchNumberResult(),
            default = someNumber(0).toDopeType(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with positional second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            queryString = "DECODE(`stringField`, \"someString\", 5, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someStringSearchNumberResult(),
            default = parameterValue.asParameter(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with named second parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "DECODE(`stringField`, \"someString\", 5, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = DecodeExpression(
            someStringField(),
            someStringSearchNumberResult(),
            default = parameterValue.asParameter(parameterName),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            queryString = "DECODE($1, \"someString\", 5, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(),
            someStringSearchNumberResult(),
            default = parameterValue2.asParameter(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode expression with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someNumber()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "DECODE(\$$parameterName, \"someString\", 5, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = DecodeExpression(
            parameterValue.asParameter(parameterName),
            someStringSearchNumberResult(),
            default = parameterValue2.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support decode extension function`() {
        val expression = someStringField()
        val searchResult = someStringSearchNumberResult()
        val expected = DecodeExpression(expression, searchResult)

        val actual = decode(expression, searchResult)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support decode extension function with default`() {
        val expression = someStringField()
        val searchResult = someStringSearchNumberResult()
        val default = someNumber().toDopeType()
        val expected = DecodeExpression(expression, searchResult, default = default)

        val actual = decode(expression, searchResult, default = default)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support decode extension function with default and different types`() {
        val expression = someStringField()
        val searchResult = someStringSearchNumberResult()
        val searchResult2 = SearchResult(someStringField(), someStringField())
        val default = someNumber().toDopeType()
        val expected = DecodeExpression(expression, searchResult, searchResult2, default = default)

        val actual = decode(expression, searchResult, searchResult2, default = default)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
