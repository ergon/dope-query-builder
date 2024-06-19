package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class VarianceExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support variance`() {
        val expected = DopeQuery(
            "VARIANCE(`numberField`)",
            emptyMap(),
        )

        val actual = VarianceExpression(someNumberField(), null).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier ALL`() {
        val expected = DopeQuery(
            "VARIANCE(ALL `numberField`)",
            emptyMap(),
        )

        val actual = VarianceExpression(someNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "VARIANCE(DISTINCT `numberField`)",
            emptyMap(),
        )

        val actual = VarianceExpression(someNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
