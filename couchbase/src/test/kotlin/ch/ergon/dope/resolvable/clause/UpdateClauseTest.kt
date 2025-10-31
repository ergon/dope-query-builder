package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someKeySpace
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support update clause`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket`",
        )
        val underTest = UpdateClause(someKeySpace())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with an alias keyspace`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` AS `keyspace`",
        )
        val underTest = UpdateClause(someKeySpace().alias("keyspace"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
