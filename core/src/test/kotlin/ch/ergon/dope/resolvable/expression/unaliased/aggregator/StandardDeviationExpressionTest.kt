package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardDeviationExpressionTest : ParameterDependentTest {
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

    @Test
    fun `should support standard deviation function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = StandardDeviationExpression(field, quantifier)

        val actual = stdDev(field, quantifier)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
