package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.sum
import kotlin.test.Test
import kotlin.test.assertEquals

class SumExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support sum`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SUM(`numberField`)",
        )
        val underTest = SumExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SUM(ALL `numberField`)",
        )
        val underTest = SumExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SUM(DISTINCT `numberField`)",
        )
        val underTest = SumExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = SumExpression(field, quantifier)

        val actual = sum(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
