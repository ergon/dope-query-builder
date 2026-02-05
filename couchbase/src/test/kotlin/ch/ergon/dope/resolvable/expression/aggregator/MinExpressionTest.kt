package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MinExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.min
import kotlin.test.Test
import kotlin.test.assertEquals

class MinExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support min`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MIN(`numberField`)",
        )
        val underTest = MinExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with quantifier ALL`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MIN(ALL `numberField`)",
        )
        val underTest = MinExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with quantifier DISTINCT`() {
        val expected = CouchbaseDopeQuery(
            queryString = "MIN(DISTINCT `numberField`)",
        )
        val underTest = MinExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = MinExpression(field, quantifier)

        val actual = min(field, quantifier)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
