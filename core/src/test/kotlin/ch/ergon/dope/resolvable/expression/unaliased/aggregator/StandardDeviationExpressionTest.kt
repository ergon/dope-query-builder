package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardDeviationExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support standard deviation`() {
        val expected = DopeQuery(
            "STDDEV(`numberField`)",
            emptyMap(),
        )
        val underTest = StandardDeviationExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier ALL`() {
        val expected = DopeQuery(
            "STDDEV(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = StandardDeviationExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "STDDEV(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = StandardDeviationExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
