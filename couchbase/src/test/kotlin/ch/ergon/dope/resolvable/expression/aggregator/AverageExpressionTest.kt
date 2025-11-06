package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.avg
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support average`() {
        val expected = CouchbaseDopeQuery(
            queryString = "AVG(`numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "AVG(ALL `numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "AVG(DISTINCT `numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = AverageExpression(field, quantifier)

        val actual = avg(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
