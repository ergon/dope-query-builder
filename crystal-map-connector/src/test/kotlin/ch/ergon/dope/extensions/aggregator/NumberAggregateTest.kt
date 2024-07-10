package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.avg
import ch.ergon.dope.extension.aggregator.mean
import ch.ergon.dope.extension.aggregator.median
import ch.ergon.dope.extension.aggregator.stdDev
import ch.ergon.dope.extension.aggregator.sum
import ch.ergon.dope.extension.aggregator.variance
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AverageExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MeanExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MedianExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.SumExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.VarianceExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberAggregateTest {
    @Test
    fun `should support avg with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = AverageExpression(field.toDopeType(), quantifier)

        val actual = avg(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support avg with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = AverageExpression(field.toDopeType(), quantifier)

        val actual = avg(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support mean with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MeanExpression(field.toDopeType(), quantifier)

        val actual = mean(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support mean with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MeanExpression(field.toDopeType(), quantifier)

        val actual = mean(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support median with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MedianExpression(field.toDopeType(), quantifier)

        val actual = median(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support median with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MedianExpression(field.toDopeType(), quantifier)

        val actual = median(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support sum with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = SumExpression(field.toDopeType(), quantifier)

        val actual = sum(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support sum with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = SumExpression(field.toDopeType(), quantifier)

        val actual = sum(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support stddev with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = StandardDeviationExpression(field.toDopeType(), quantifier)

        val actual = stdDev(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support stddev with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = StandardDeviationExpression(field.toDopeType(), quantifier)

        val actual = stdDev(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support variance with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = VarianceExpression(field.toDopeType(), quantifier)

        val actual = variance(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support variance with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = VarianceExpression(field.toDopeType(), quantifier)

        val actual = variance(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
