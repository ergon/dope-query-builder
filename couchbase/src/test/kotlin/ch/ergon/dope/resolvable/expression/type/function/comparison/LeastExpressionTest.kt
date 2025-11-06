package ch.ergon.dope.resolvable.expression.type.function.comparison

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class LeastExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support greatest expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(`numberField`, `anotherNumberField`)",
        )
        val underTest = LeastExpression(someNumberField(), someNumberField("anotherNumberField"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with positional parameter`() {
        val parameterValue = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeastExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with positional parameter as value`() {
        val parameterValue = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(`numberField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LeastExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with all positional parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = LeastExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with named parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeastExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with named parameter as value`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(`numberField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LeastExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with all named parameters`() {
        val parameterValue = someNumber()
        val parameterName = "param1"
        val parameterValue2 = someNumber()
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = LeastExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least expression with mixed named and positional parameters`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val parameterValue2 = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "LEAST(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = LeastExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression function`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val expected = LeastExpression(numberField, numberField2)

        val actual = leastOf(numberField, numberField2)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greatest expression function with additional expressions`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val numberField3 = someNumberField()
        val numberField4 = someNumberField()
        val expected = LeastExpression(numberField, numberField2, listOf(numberField3, numberField4))

        val actual = leastOf(numberField, numberField2, numberField3, numberField4)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
