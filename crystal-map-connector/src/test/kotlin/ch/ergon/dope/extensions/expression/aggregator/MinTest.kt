package ch.ergon.dope.extensions.expression.aggregator

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.aggregator.min
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MinExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MinTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support min with CMJsonField Number`() {
        val field = someCMNumberField()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonField Number and type`() {
        val field = someCMNumberField()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonField string`() {
        val field = someCMStringField()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonField string and type`() {
        val field = someCMStringField()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonField boolean`() {
        val field = someCMBooleanField()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonField boolean and type`() {
        val field = someCMBooleanField()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList Number`() {
        val field = someCMNumberList()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList Number and type`() {
        val field = someCMNumberList()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList string`() {
        val field = someCMStringList()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList string and type`() {
        val field = someCMStringList()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList Boolean`() {
        val field = someCMBooleanList()
        val quantifier = null
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support min with CMJsonList Boolean and type`() {
        val field = someCMBooleanList()
        val quantifier = ALL
        val expected = MinExpression(field.toDopeType(), quantifier)

        val actual = min(field, ALL)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
