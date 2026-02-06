package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ReverseExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support reverse`() {
        val expected = CouchbaseDopeQuery(
            queryString = "REVERSE(`stringField`)",
        )
        val underTest = ReverseExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse with positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "REVERSE($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ReverseExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "REVERSE(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ReverseExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support reverse function type`() {
        val inStr = someStringField("inStr")
        val expected = ReverseExpression(inStr)

        val actual = inStr.reverse()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support reverse function string`() {
        val inStr = someString()
        val expected = ReverseExpression(inStr.toDopeType())

        val actual = inStr.reverse()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
