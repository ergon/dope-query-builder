package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.couchbase.resolvable.keyspace.SystemBuckets
import kotlin.test.Test
import kotlin.test.assertSame

class SystemBucketsTest {
    @Test
    fun `should expose grouped and top-level references to same keyspace instances`() {
        assertSame(SystemBuckets.dataContainers.keyspaces, SystemBuckets.keyspaces)
        assertSame(SystemBuckets.monitoring.prepareds, SystemBuckets.prepareds)
        assertSame(SystemBuckets.security.nodes, SystemBuckets.nodes)
        assertSame(SystemBuckets.other.allSequences, SystemBuckets.allSequences)
    }
}
