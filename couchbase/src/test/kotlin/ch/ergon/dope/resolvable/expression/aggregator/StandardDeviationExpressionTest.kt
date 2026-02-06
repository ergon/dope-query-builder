package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.stdDev
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardDeviationExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support standard deviation`() {
        val expected = CouchbaseDopeQuery(
            queryString = "STDDEV(`numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "STDDEV(ALL `numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "STDDEV(DISTINCT `numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = StandardDeviationExpression(field, quantifier)

        val actual = stdDev(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
