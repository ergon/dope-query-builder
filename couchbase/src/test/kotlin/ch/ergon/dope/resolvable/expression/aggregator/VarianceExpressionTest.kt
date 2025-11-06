package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.VarianceExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.variance
import kotlin.test.Test
import kotlin.test.assertEquals

class VarianceExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support variance`() {
        val expected = CouchbaseDopeQuery(
            queryString = "VARIANCE(`numberField`)",
        )
        val underTest = VarianceExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "VARIANCE(ALL `numberField`)",
        )
        val underTest = VarianceExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "VARIANCE(DISTINCT `numberField`)",
        )
        val underTest = VarianceExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = VarianceExpression(field, quantifier)

        val actual = variance(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
