package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someKeyspace
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import kotlin.test.Test
import kotlin.test.assertEquals

class FromClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support from`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * FROM `someBucket`",
        )
        val underTest = FromClause(someKeyspace(), someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from with alias keyspace`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * FROM `someBucket` AS `keyspace`",
        )
        val underTest = FromClause(someKeyspace().alias("keyspace"), someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from function`() {
        val keyspace = someKeyspace()
        val parentClause = someSelectClause()
        val expected = FromClause(keyspace, parentClause)

        val actual = parentClause.from(keyspace)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
