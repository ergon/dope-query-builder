package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.DopeQueryManager
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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAggTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support array_agg with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support array_agg with CMList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = ArrayAggregateExpression(field.toDopeType(), quantifier)

        val actual = arrayAggregate(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
