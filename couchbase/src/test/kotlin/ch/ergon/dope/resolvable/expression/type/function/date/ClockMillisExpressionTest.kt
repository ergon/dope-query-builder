package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ClockMillisExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support CLOCK_MILLIS`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CLOCK_MILLIS()",
        )
        val underTest = ClockMillisExpression()

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support clockMillis extension`() {
        val expected = ClockMillisExpression()
        val actual = clockMillis()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
