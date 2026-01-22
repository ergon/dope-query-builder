package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support delete`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket`",
        )
        val underTest = DeleteClause(someKeyspace())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete with alias keyspace`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` AS `keyspace`",
        )
        val underTest = DeleteClause(someKeyspace().alias("keyspace"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
