package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.keyspace.SystemBuckets
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.resolvable.expression.type.alias
import kotlin.test.Test
import kotlin.test.assertEquals

class SystemTest {
    @Test
    fun `should support system prepareds keyspace`() {
        val tasksCache = SystemBuckets.tasksCache
        val expected = "SELECT `tasks_cache`.`delay` FROM `system`:`tasks_cache`"

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
        val allSequences = SystemBuckets.allSequences
        val expected = "SELECT `all_sequences`.`name` FROM `system`:`all_sequences`"

        val actual = QueryBuilder
            .select(
                allSequences.nameField,
            ).from(
                allSequences,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased system keyspace`() {
        val idx = SystemBuckets.indexes.alias("idx")
        val expected = "SELECT `idx`.`name`, `idx`.`state` FROM `system`:`indexes` AS `idx`"

        val actual = QueryBuilder
            .select(
                idx.nameField,
                idx.state,
            ).from(
                idx,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dual system bucket`() {
        val dualBucket = SystemBuckets.dual
        val expected = "SELECT 1 AS `num` FROM `system`:`dual`"

        val actual = QueryBuilder
            .select(
                1.alias("num"),
            ).from(
                dualBucket,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support bucket info system bucket`() {
        val infoBucket = SystemBuckets.bucketInfo
        val expected = "SELECT * FROM `system`:`bucket_info`"

        val actual = QueryBuilder
            .selectFrom(
                infoBucket,
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
