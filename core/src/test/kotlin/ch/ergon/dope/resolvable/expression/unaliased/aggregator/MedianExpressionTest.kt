package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MedianExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support median`() {
        val expected = DopeQuery(
            "MEDIAN(`numberField`)",
            emptyMap(),
        )
        val underTest = MedianExpression(CMNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier ALL`() {
        val expected = DopeQuery(
            "MEDIAN(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = MedianExpression(CMNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MEDIAN(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = MedianExpression(CMNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median function`() {
        val field = CMNumberField()
        val quantifier = DISTINCT
        val expected = MedianExpression(field, quantifier)

        val actual = median(field, quantifier)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
