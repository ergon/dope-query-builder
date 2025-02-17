package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.aggregate.sum
import kotlin.test.Test
import kotlin.test.assertEquals

class SumExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support sum`() {
        val expected = DopeQuery(
            queryString = "SUM(`numberField`)",
        )
        val underTest = SumExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "SUM(ALL `numberField`)",
        )
        val underTest = SumExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "SUM(DISTINCT `numberField`)",
        )
        val underTest = SumExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = SumExpression(field, quantifier)

        val actual = sum(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
