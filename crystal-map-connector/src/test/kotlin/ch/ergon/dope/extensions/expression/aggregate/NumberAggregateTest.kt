package ch.ergon.dope.extensions.expression.aggregate

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.aggregate.avg
import ch.ergon.dope.extension.expression.aggregate.mean
import ch.ergon.dope.extension.expression.aggregate.median
import ch.ergon.dope.extension.expression.aggregate.stdDev
import ch.ergon.dope.extension.expression.aggregate.sum
import ch.ergon.dope.extension.expression.aggregate.variance
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MeanExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.VarianceExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberAggregateTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support avg with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = AverageExpression(field.toDopeType(), quantifier)

        val actual = avg(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support avg with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = AverageExpression(field.toDopeType(), quantifier)

        val actual = avg(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mean with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MeanExpression(field.toDopeType(), quantifier)

        val actual = mean(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support mean with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MeanExpression(field.toDopeType(), quantifier)

        val actual = mean(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support median with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MedianExpression(field.toDopeType(), quantifier)

        val actual = median(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support median with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MedianExpression(field.toDopeType(), quantifier)

        val actual = median(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sum with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = SumExpression(field.toDopeType(), quantifier)

        val actual = sum(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support sum with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = SumExpression(field.toDopeType(), quantifier)

        val actual = sum(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support stddev with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = StandardDeviationExpression(field.toDopeType(), quantifier)

        val actual = stdDev(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support stddev with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = StandardDeviationExpression(field.toDopeType(), quantifier)

        val actual = stdDev(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support variance with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = VarianceExpression(field.toDopeType(), quantifier)

        val actual = variance(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support variance with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = VarianceExpression(field.toDopeType(), quantifier)

        val actual = variance(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
