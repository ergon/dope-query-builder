package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.avg
import ch.ergon.dope.extension.aggregator.mean
import ch.ergon.dope.extension.aggregator.median
import ch.ergon.dope.extension.aggregator.stddev
import ch.ergon.dope.extension.aggregator.sum
import ch.ergon.dope.extension.aggregator.variance
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class NumberAggregateTest {
    @Test
    fun `should support avg with CMField Number`() {
        val actual: String = avg(someCMNumberField()).toDopeQuery().queryString

        assertEquals("AVG(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support avg distinct with CMField Number`() {
        val actual: String = avg(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("AVG(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support mean with CMField Number`() {
        val actual: String = mean(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MEAN(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support mean distinct with CMField Number`() {
        val actual: String = mean(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MEAN(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support median with CMField Number`() {
        val actual: String = median(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MEDIAN(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support median distinct with CMField Number`() {
        val actual: String = median(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MEDIAN(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support sum with CMField Number`() {
        val actual: String = sum(someCMNumberField()).toDopeQuery().queryString

        assertEquals("SUM(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support sum distinct with CMField Number`() {
        val actual: String = sum(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("SUM(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support stddev with CMField Number`() {
        val actual: String = stddev(someCMNumberField()).toDopeQuery().queryString

        assertEquals("STDDEV(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support stddev distinct with CMField Number`() {
        val actual: String = stddev(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("STDDEV(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support variance with CMField Number`() {
        val actual: String = variance(someCMNumberField()).toDopeQuery().queryString

        assertEquals("VARIANCE(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support variance distinct with CMField Number`() {
        val actual: String = variance(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("VARIANCE(DISTINCT `someNumberField`)", actual)
    }
}
