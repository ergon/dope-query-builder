package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.DenseRank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.DenseRankWithReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.denseRank
import kotlin.test.Test
import kotlin.test.assertEquals

class DenseRankTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support dense rank with reference`() {
        val expected = CouchbaseDopeQuery(
            "DENSE_RANK() OVER `ref`",
        )
        val underTest = DenseRankWithReference("ref")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank with ordering term`() {
        val expected = CouchbaseDopeQuery(
            "DENSE_RANK() OVER (ORDER BY `stringField`)",
        )
        val underTest = DenseRank(listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank with ordering term and partition`() {
        val expected = CouchbaseDopeQuery(
            "DENSE_RANK() OVER (PARTITION BY `stringField`, `numberField` ORDER BY `stringField`)",
        )
        val underTest = DenseRank(listOf(someOrderingTerm()), listOf(someStringField(), someNumberField()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank function with reference`() {
        val windowReference = "ref"
        val expected = DenseRankWithReference(windowReference)

        val actual = denseRank(windowReference)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support dense rank function`() {
        val windowOrderClause = listOf(someOrderingTerm())
        val windowPartitionClause = listOf(someStringField(), someNumberField())
        val expected = DenseRank(windowOrderClause, windowPartitionClause)

        val actual = denseRank(windowOrderClause, windowPartitionClause)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
