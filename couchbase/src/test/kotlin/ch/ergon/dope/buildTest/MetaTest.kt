package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaTest {
    @Test
    fun `should support meta expression with bucket`() {
        val expected = "SELECT META(`someBucket`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta(someBucket()),
            ).from(
                someBucket(),
            ).build(CouchbaseResolver).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression without a bucket`() {
        val expected = "SELECT META() FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta(),
            ).from(
                someBucket(),
            ).build(CouchbaseResolver).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression fields`() {
        val expected = "SELECT META().`cas`, META().`expiration`, META().`flags`, META().`id`, " +
            "META().`keyspace`, META().`type` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta().cas,
                meta().expiration,
                meta().flags,
                meta().id,
                meta().keyspace,
                meta().type,
            ).from(
                someBucket(),
            ).build(CouchbaseResolver).queryString

        assertEquals(expected, actual)
    }
}
