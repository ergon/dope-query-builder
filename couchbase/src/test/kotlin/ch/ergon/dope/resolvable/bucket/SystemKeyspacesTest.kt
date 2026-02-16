package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.keyspace.system
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SystemKeyspacesTest {
    @Test
    fun `should keep keyspace identity separate from colliding field names`() {
        val allSequences = system().allSequences

        assertEquals("system:all_sequences", allSequences.name)
        assertNull(allSequences.scope)
        assertEquals("all_sequences", allSequences.alias)
    }

    @Test
    fun `should select renamed field from system keyspace`() {
        val allSequences = system().allSequences
        val expected = "SELECT `all_sequences`.`name` FROM `system`:`all_sequences` AS `all_sequences`"

        val actual = QueryBuilder
            .select(allSequences.nameField)
            .from(allSequences)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }
}
