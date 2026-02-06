package ch.ergon.dope.resolvable.expression.type.function.type

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

class IsStringExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is string expression with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ISSTRING(`stringField`)",
        )
        val underTest = IsStringExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is string expression with positional parameter`() {
        val parameterValue = someString()
        val expected = CouchbaseDopeQuery(
            queryString = "ISSTRING($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IsStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is string expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ISSTRING(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IsStringExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is string extension`() {
        val string = someString().toDopeType()
        val expected = IsStringExpression(string)

        val actual = string.isString()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
