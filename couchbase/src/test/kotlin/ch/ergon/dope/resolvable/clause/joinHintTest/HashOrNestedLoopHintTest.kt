package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_BUILD
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.HASH_PROBE
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import kotlin.test.Test
import kotlin.test.assertEquals

class HashOrNestedLoopHintTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support use hash build`() {
        val expected = CouchbaseDopeQuery(
            queryString = "HASH (BUILD)",
        )
        val underTest = HASH_BUILD

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use hash probe`() {
        val expected = CouchbaseDopeQuery(
            queryString = "HASH (PROBE)",
        )
        val underTest = HASH_PROBE

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use nested loop`() {
        val expected = CouchbaseDopeQuery(
            queryString = "NL",
        )
        val underTest = NESTED_LOOP

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
