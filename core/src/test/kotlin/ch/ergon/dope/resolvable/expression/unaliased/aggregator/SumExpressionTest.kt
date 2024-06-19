package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class SumExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support sum`() {
        val expected = DopeQuery(
            "SUM(`numberField`)",
            emptyMap(),
        )

        val actual = SumExpression(someNumberField(), null).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier ALL`() {
        val expected = DopeQuery(
            "SUM(ALL `numberField`)",
            emptyMap(),
        )

        val actual = SumExpression(someNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support sum with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "SUM(DISTINCT `numberField`)",
            emptyMap(),
        )

        val actual = SumExpression(someNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
