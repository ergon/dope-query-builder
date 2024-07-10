package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.max
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MaxExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxTest {
    @Test
    fun `should support max with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support max with CMList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = MaxExpression(field.toDopeType(), quantifier)

        val actual = max(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
