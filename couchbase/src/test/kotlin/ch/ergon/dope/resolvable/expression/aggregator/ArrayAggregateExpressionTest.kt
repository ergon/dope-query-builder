package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.arrayAggregate
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAggregateExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support array aggregate`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_AGG(`stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_AGG(ALL `stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate function`() {
        val field = someStringField()
        val quantifier = DISTINCT
        val expected = ArrayAggregateExpression(field, quantifier)

        val actual = arrayAggregate(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
