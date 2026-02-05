package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MaxExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.max
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support max`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MAX(`numberField`)",
        )
        val underTest = MaxExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MAX(ALL `numberField`)",
        )
        val underTest = MaxExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MAX(DISTINCT `numberField`)",
        )
        val underTest = MaxExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max function`() {
        val field = someStringField()
        val quantifier = DISTINCT
        val expected = MaxExpression(field, quantifier)

        val actual = max(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
