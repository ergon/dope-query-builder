package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBucket
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
        val underTest = UpdateClause(someBucket())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with an alias bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` AS `bucket`",
        )
        val underTest = UpdateClause(someBucket().alias("bucket"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
