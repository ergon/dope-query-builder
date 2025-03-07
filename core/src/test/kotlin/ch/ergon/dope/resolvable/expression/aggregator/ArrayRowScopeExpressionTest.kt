package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.arrayAggregate
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRowScopeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support array aggregate`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `stringField`)",
        )
        val underTest = ArrayAggregateExpression(someStringField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate function`() {
        val field = someStringField()
        val quantifier = DISTINCT
        val expected = ArrayAggregateExpression(field, quantifier)

        val actual = arrayAggregate(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
