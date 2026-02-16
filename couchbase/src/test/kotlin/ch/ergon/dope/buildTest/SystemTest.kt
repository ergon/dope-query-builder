package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.keyspace.system
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import kotlin.test.Test
import kotlin.test.assertEquals

class SystemTest {
    @Test
    fun `should support system prepareds keyspace`() {
        val tasksCache = system().tasksCache
        val expected = "SELECT `tasks_cache`.`delay` FROM `system`:`tasks_cache` AS `tasks_cache`"

        val actual = QueryBuilder
            .select(
                tasksCache.delay,
            ).from(
                tasksCache,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support system all-sequences keyspace`() {
        val allSequences = system().allSequences
        val expected = "SELECT `all_sequences`.`name` FROM `system`:`all_sequences` AS `all_sequences`"

        val actual = QueryBuilder
            .select(
                allSequences.nameField,
            ).from(
                allSequences,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
