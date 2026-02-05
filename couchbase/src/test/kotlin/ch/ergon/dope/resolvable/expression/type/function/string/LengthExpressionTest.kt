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

class LengthExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support length with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "LENGTH(`stringField`)",
        )
        val underTest = LengthExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "LENGTH($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = LengthExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "LENGTH(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = LengthExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support length function type`() {
        val inStr = someStringField("inStr")
        val expected = LengthExpression(inStr)

        val actual = inStr.length()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support length function string`() {
        val inStr = someString()
        val expected = LengthExpression(inStr.toDopeType())

        val actual = inStr.toDopeType().length()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
