package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNotMissingExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is not missing`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` IS NOT MISSING",
        )
        val underTest = IsNotMissingExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing function`() {
        val field = someStringField()
        val expected = IsNotMissingExpression(field)

        val actual = field.isNotMissing()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
