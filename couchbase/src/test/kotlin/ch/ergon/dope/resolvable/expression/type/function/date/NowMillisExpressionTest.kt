package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NowMillisExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support NOW_MILLIS`() {
        val expected = CouchbaseDopeQuery(
            queryString = "NOW_MILLIS()",
        )
        val underTest = NowMillisExpression()

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nowEpochMillis extension`() {
        val expected = NowMillisExpression()
        val actual = nowEpochMillis()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
