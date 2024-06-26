package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.avg
import ch.ergon.dope.extension.aggregator.mean
import ch.ergon.dope.extension.aggregator.median
import ch.ergon.dope.extension.aggregator.stdDev
import ch.ergon.dope.extension.aggregator.sum
import ch.ergon.dope.extension.aggregator.variance
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberAggregateTest {
    @Test
    fun `should support avg with CMField Number`() {
        val actual: String = avg(someCMNumberField()).toDopeQuery().queryString

        assertEquals("AVG(`CMNumberField`)", actual)
    }

    @Test
    fun `should support avg all with CMField Number`() {
        val actual: String = avg(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("AVG(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support avg distinct with CMField Number`() {
        val actual: String = avg(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("AVG(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support mean with CMField Number`() {
        val actual: String = mean(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MEAN(`CMNumberField`)", actual)
    }

    @Test
    fun `should support mean all with CMField Number`() {
        val actual: String = mean(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("MEAN(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support mean distinct with CMField Number`() {
        val actual: String = mean(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MEAN(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support median with CMField Number`() {
        val actual: String = median(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MEDIAN(`CMNumberField`)", actual)
    }

    @Test
    fun `should support median all with CMField Number`() {
        val actual: String = median(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("MEDIAN(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support median distinct with CMField Number`() {
        val actual: String = median(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MEDIAN(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support sum with CMField Number`() {
        val actual: String = sum(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SUM(`CMNumberField`)", actual)
    }

    @Test
    fun `should support sum all with CMField Number`() {
        val actual: String = sum(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("SUM(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support sum distinct with CMField Number`() {
        val actual: String = sum(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("SUM(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support stddev with CMField Number`() {
        val actual: String = stdDev(someCMNumberField()).toDopeQuery().queryString

        assertEquals("STDDEV(`CMNumberField`)", actual)
    }

    @Test
    fun `should support stddev all with CMField Number`() {
        val actual: String = stdDev(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("STDDEV(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support stddev distinct with CMField Number`() {
        val actual: String = stdDev(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("STDDEV(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support variance with CMField Number`() {
        val actual: String = variance(someCMNumberField()).toDopeQuery().queryString

        assertEquals("VARIANCE(`CMNumberField`)", actual)
    }

    @Test
    fun `should support variance all with CMField Number`() {
        val actual: String = variance(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("VARIANCE(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support variance distinct with CMField Number`() {
        val actual: String = variance(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("VARIANCE(DISTINCT `CMNumberField`)", actual)
    }
}
