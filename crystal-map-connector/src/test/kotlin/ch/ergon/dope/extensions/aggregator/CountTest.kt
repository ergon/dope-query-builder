package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.aggregator.count
import ch.ergon.dope.helper.ManagerDependentTest
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

class CountTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support count with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support count with CMJsonList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = CountExpression(field.toDopeType(), quantifier)

        val actual = count(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
