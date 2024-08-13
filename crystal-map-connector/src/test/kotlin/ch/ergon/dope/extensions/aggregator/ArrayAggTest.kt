package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.arrayAggregate
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.ArrayAggregateExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAggTest {
    @Test
    fun `should support array_agg with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support array_agg with CMJsonList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
