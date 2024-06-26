package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.arrayAggregate
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ArrayAggTest {
    @Test
    fun `should support array_agg with CMField Number`() {
        val actual: String = arrayAggregate(someCMNumberField()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someNumberField`)", actual)
    }

    @Test
    fun `should support array_agg all with CMField Number`() {
        val actual: String = arrayAggregate(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMField Number`() {
        val actual: String = arrayAggregate(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support array_agg with CMField String`() {
        val actual: String = arrayAggregate(someCMStringField()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someStringField`)", actual)
    }

    @Test
    fun `should support array_agg all with CMField String`() {
        val actual: String = arrayAggregate(someCMStringField(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someStringField`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMField String`() {
        val actual: String = arrayAggregate(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someStringField`)", actual)
    }

    @Test
    fun `should support array_agg with CMField Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someBooleanField`)", actual)
    }

    @Test
    fun `should support array_agg all with CMField Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanField(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someBooleanField`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMField Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someBooleanField`)", actual)
    }

    @Test
    fun `should support array_agg with CMList Number`() {
        val actual: String = arrayAggregate(someCMNumberList()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someNumberList`)", actual)
    }

    @Test
    fun `should support array_agg all with CMList Number`() {
        val actual: String = arrayAggregate(someCMNumberList(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someNumberList`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMList Number`() {
        val actual: String = arrayAggregate(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someNumberList`)", actual)
    }

    @Test
    fun `should support array_agg with CMList String`() {
        val actual: String = arrayAggregate(someCMStringList()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someStringList`)", actual)
    }

    @Test
    fun `should support array_agg all with CMList String`() {
        val actual: String = arrayAggregate(someCMStringList(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someStringList`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMList String`() {
        val actual: String = arrayAggregate(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someStringList`)", actual)
    }

    @Test
    fun `should support array_agg with CMList Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(`someBooleanList`)", actual)
    }

    @Test
    fun `should support array_agg all with CMList Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanList(), ALL).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(ALL `someBooleanList`)", actual)
    }

    @Test
    fun `should support array_agg distinct with CMList Boolean`() {
        val actual: String = arrayAggregate(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("ARRAY_AGG(DISTINCT `someBooleanList`)", actual)
    }
}
