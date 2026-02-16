package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.couchbase.resolvable.keyspace.system
import kotlin.test.Test
import kotlin.test.assertSame

class SystemNamespaceTest {
    @Test
    fun `should expose grouped and top-level references to same keyspace instances`() {
        val namespace = system()

        assertSame(namespace.dataContainers.keyspaces, namespace.keyspaces)
        assertSame(namespace.monitoring.prepareds, namespace.prepareds)
        assertSame(namespace.security.nodes, namespace.nodes)
        assertSame(namespace.other.allSequences, namespace.allSequences)
    }
}
