package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToNumberExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support to number expression with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TONUMBER(`stringField`)",
        )
        val underTest = ToNumberExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number expression with positional parameter`() {
        val parameterValue = someString()
        val expected = CouchbaseDopeQuery(
            queryString = "TONUMBER($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ToNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "TONUMBER(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ToNumberExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number extension`() {
        val string = someStringField()
        val expected = ToNumberExpression(string)

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with string`() {
        val string = someString()
        val expected = ToNumberExpression(string.toDopeType())

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToNumberExpression(boolean.toDopeType())

        val actual = boolean.toNumber()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with filterChars`() {
        val string = someStringField()
        val filterChars = someStringField()
        val expected = ToNumberExpression(string, filterChars)

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with filterChars as string`() {
        val string = someStringField()
        val filterChars = someString()
        val expected = ToNumberExpression(string, filterChars.toDopeType())

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with string and filterChars`() {
        val string = someString()
        val filterChars = someStringField()
        val expected = ToNumberExpression(string.toDopeType(), filterChars)

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support to number extension with string and filterChars as string`() {
        val string = someString()
        val filterChars = someString()
        val expected = ToNumberExpression(string.toDopeType(), filterChars.toDopeType())

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
