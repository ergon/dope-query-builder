package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlEncodeExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support url encode expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "URL_ENCODE(`stringField`)",
        )
        val underTest = UrlEncodeExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support url encode extension type`() {
        val string = someStringField()
        val expected = UrlEncodeExpression(string)

        val actual = urlEncode(string)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support url encode extension string`() {
        val string = someString()
        val expected = UrlEncodeExpression(string.toDopeType())

        val actual = urlEncode(string)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
