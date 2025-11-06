package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBucket
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
        val underTest = FromClause(someBucket(), someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from with alias bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * FROM `someBucket` AS `bucket`",
        )
        val underTest = FromClause(someBucket().alias("bucket"), someSelectClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support from function`() {
        val bucket = someBucket()
        val parentClause = someSelectClause()
        val expected = FromClause(bucket, parentClause)

        val actual = parentClause.from(bucket)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
