package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.aggregator.arrayAggregate
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAggTest {
    @Test
    fun `should support array_agg with CMField Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMField Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMField Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with CMField String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMField String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMField String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMStringField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMField Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMBooleanField`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with CMList Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMList Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMList Number`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMNumberList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMNumberList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with CMList String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMList String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMList String`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMStringList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMStringList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(`CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanList()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg all with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(ALL `CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanList(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array_agg distinct with CMList Boolean`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AGG(DISTINCT `CMBooleanList`)",
            parameters = emptyMap(),
        )

        val actual = arrayAggregate(someCMBooleanList(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
