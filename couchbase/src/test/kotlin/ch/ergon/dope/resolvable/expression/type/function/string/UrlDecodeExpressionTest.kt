package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlDecodeExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support url decode expression`() {
        val expected = CouchbaseDopeQuery(
            queryString = "URL_DECODE(`stringField`)",
        )
        val underTest = UrlDecodeExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support url decode extension type`() {
        val encodedString = someStringField()
        val expected = UrlDecodeExpression(encodedString)

        val actual = urlDecode(encodedString)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support url decode extension string`() {
        val encodedString = someString()
        val expected = UrlDecodeExpression(encodedString.toDopeType())

        val actual = urlDecode(encodedString)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
