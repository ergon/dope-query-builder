package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MeanExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support mean`() {
        val expected = DopeQuery(
            "MEAN(`numberField`)",
            emptyMap(),
        )

        val actual = MeanExpression(someNumberField(), null).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier ALL`() {
        val expected = DopeQuery(
            "MEAN(ALL `numberField`)",
            emptyMap(),
        )

        val actual = MeanExpression(someNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MEAN(DISTINCT `numberField`)",
            emptyMap(),
        )

        val actual = MeanExpression(someNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
