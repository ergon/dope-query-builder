package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class SumExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support sum`() {
        val expected = DopeQuery(
            "SUM(`numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = SumExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier ALL`() {
        val expected = DopeQuery(
            "SUM(ALL `numberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = SumExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "SUM(DISTINCT `numberField`)",
            emptyMap(),
            emptyList(),
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
