package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Rank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.RankWithReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.rank
import kotlin.test.Test
import kotlin.test.assertEquals

class RankTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support rank with reference`() {
        val expected = CouchbaseDopeQuery(
            "RANK() OVER `ref`",
        )
        val underTest = RankWithReference("ref")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank with order clause`() {
        val expected = CouchbaseDopeQuery(
            "RANK() OVER (ORDER BY `stringField`)",
        )
        val underTest = Rank(windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank with partition and order clause`() {
        val expected = CouchbaseDopeQuery(
            "RANK() OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = Rank(
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank function with reference`() {
        val windowReference = "ref"
        val expected = RankWithReference(windowReference)

        val actual = rank(windowReference)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support rank function with partition and order clause`() {
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = Rank(windowPartitionClause, windowOrderClause)

        val actual = rank(windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
