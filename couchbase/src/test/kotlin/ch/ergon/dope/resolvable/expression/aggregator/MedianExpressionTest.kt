package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.median
import kotlin.test.Test
import kotlin.test.assertEquals

class MedianExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support median`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MEDIAN(`numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MEDIAN(ALL `numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MEDIAN(DISTINCT `numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = MedianExpression(field, quantifier)

        val actual = median(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
