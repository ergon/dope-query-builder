package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.someKeyspace
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaTest {
    @Test
    fun `should support meta expression with keyspace`() {
        val expected = "SELECT META(`someBucket`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta(someKeyspace()),
            ).from(
                someKeyspace(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression without a keyspace`() {
        val expected = "SELECT META() FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta(),
            ).from(
                someKeyspace(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta expression fields`() {
        val expected = "SELECT META().`cas`, META().`expiration`, META().`flags`, META().`id`, " +
            "META().`type` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                meta().cas,
                meta().expiration,
                meta().flags,
                meta().id,
                meta().type,
            ).from(
                someKeyspace(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
