package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.CumeDist
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.CumeDistWithReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.cumeDist
import kotlin.test.Test
import kotlin.test.assertEquals

class CumeDistTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support cume dist with reference`() {
        val expected = CouchbaseDopeQuery(
            "CUME_DIST() OVER `ref`",
        )
        val underTest = CumeDistWithReference("ref")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support cume dist with ordering term`() {
        val expected = CouchbaseDopeQuery(
            "CUME_DIST() OVER (ORDER BY `stringField`)",
        )
        val underTest = CumeDist(listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support cume dist with ordering term and partition`() {
        val expected = CouchbaseDopeQuery(
            "CUME_DIST() OVER (PARTITION BY `stringField`, `numberField` ORDER BY `stringField`)",
        )
        val underTest = CumeDist(listOf(someOrderingTerm()), listOf(someStringField(), someNumberField()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support cume dist function with reference`() {
        val windowReference = "ref"
        val expected = CumeDistWithReference(windowReference)

        val actual = cumeDist(windowReference)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support cume dist function`() {
        val windowOrderClause = listOf(someOrderingTerm())
        val windowPartitionClause = listOf(someStringField(), someNumberField())
        val expected = CumeDist(windowOrderClause, windowPartitionClause)

        val actual = cumeDist(windowOrderClause, windowPartitionClause)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
