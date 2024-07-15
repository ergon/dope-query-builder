package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.count
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class CountTest {
    @Test
    fun `should support count with CMField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support count with CMList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
