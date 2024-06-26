package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.min
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

class MinTest {
    @Test
    fun `should support min with CMField Number`() {
        val actual: String = min(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MIN(`CMNumberField`)", actual)
    }

    @Test
    fun `should support min all with CMField Number`() {
        val actual: String = min(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField Number`() {
        val actual: String = min(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support min with CMField String`() {
        val actual: String = min(someCMStringField()).toDopeQuery().queryString

        assertEquals("MIN(`CMStringField`)", actual)
    }

    @Test
    fun `should support min all with CMField String`() {
        val actual: String = min(someCMStringField(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMStringField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField String`() {
        val actual: String = min(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMStringField`)", actual)
    }

    @Test
    fun `should support min with CMField Boolean`() {
        val actual: String = min(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("MIN(`CMBooleanField`)", actual)
    }

    @Test
    fun `should support min all with CMField Boolean`() {
        val actual: String = min(someCMBooleanField(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMBooleanField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField Boolean`() {
        val actual: String = min(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMBooleanField`)", actual)
    }

    @Test
    fun `should support min with CMList Number`() {
        val actual: String = min(someCMNumberList()).toDopeQuery().queryString

        assertEquals("MIN(`CMNumberList`)", actual)
    }

    @Test
    fun `should support min all with CMList Number`() {
        val actual: String = min(someCMNumberList(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMNumberList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList Number`() {
        val actual: String = min(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMNumberList`)", actual)
    }

    @Test
    fun `should support min with CMList String`() {
        val actual: String = min(someCMStringList()).toDopeQuery().queryString

        assertEquals("MIN(`CMStringList`)", actual)
    }

    @Test
    fun `should support min all with CMList String`() {
        val actual: String = min(someCMStringList(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMStringList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList String`() {
        val actual: String = min(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMStringList`)", actual)
    }

    @Test
    fun `should support min with CMList Boolean`() {
        val actual: String = min(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("MIN(`CMBooleanList`)", actual)
    }

    @Test
    fun `should support min all with CMList Boolean`() {
        val actual: String = min(someCMBooleanList(), ALL).toDopeQuery().queryString

        assertEquals("MIN(ALL `CMBooleanList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList Boolean`() {
        val actual: String = min(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `CMBooleanList`)", actual)
    }
}
