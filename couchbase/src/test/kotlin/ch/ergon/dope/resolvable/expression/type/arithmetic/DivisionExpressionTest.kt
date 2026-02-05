package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DivisionExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support division`() {
        val expected = CouchbaseDopeQuery(
            queryString = "(`numberField` / `numberField`)",
        )
        val underTest = DivisionExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with parameter`() {
        val parameterValue = 4
        val expected = CouchbaseDopeQuery(
            queryString = "($1 / `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DivisionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = CouchbaseDopeQuery(
            queryString = "($1 / $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = DivisionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with second parameter`() {
        val parameterValue = 4
        val expected = CouchbaseDopeQuery(
            queryString = "(`numberField` / $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = DivisionExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with named parameter`() {
        val parameterValue = 4
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "(`numberField` / \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = DivisionExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with mixed parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val parameterName = "param1"
        val expected = CouchbaseDopeQuery(
            queryString = "(\$$parameterName / $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = DivisionExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = DivisionExpression(left, right)

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support division function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = DivisionExpression(left, right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support division function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = DivisionExpression(left.toDopeType(), right)

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
