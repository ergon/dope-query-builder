package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is missing`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` IS MISSING",
        )
        val underTest = IsMissingExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing function`() {
        val field = someStringField()
        val expected = IsMissingExpression(field)

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
