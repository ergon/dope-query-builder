package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.max
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

class MaxTest {
    @Test
    fun `should support max with CMField Number`() {
        val actual: String = max(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MAX(`CMNumberField`)", actual)
    }

    @Test
    fun `should support max all with CMField Number`() {
        val actual: String = max(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMNumberField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField Number`() {
        val actual: String = max(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMNumberField`)", actual)
    }

    @Test
    fun `should support max with CMField String`() {
        val actual: String = max(someCMStringField()).toDopeQuery().queryString

        assertEquals("MAX(`CMStringField`)", actual)
    }

    @Test
    fun `should support max all with CMField String`() {
        val actual: String = max(someCMStringField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMStringField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField String`() {
        val actual: String = max(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMStringField`)", actual)
    }

    @Test
    fun `should support max with CMField Boolean`() {
        val actual: String = max(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("MAX(`CMBooleanField`)", actual)
    }

    @Test
    fun `should support max all with CMField Boolean`() {
        val actual: String = max(someCMBooleanField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMBooleanField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField Boolean`() {
        val actual: String = max(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMBooleanField`)", actual)
    }

    @Test
    fun `should support max with CMList Number`() {
        val actual: String = max(someCMNumberList()).toDopeQuery().queryString

        assertEquals("MAX(`CMNumberList`)", actual)
    }

    @Test
    fun `should support max all with CMList Number`() {
        val actual: String = max(someCMNumberList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMNumberList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList Number`() {
        val actual: String = max(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMNumberList`)", actual)
    }

    @Test
    fun `should support max with CMList String`() {
        val actual: String = max(someCMStringList()).toDopeQuery().queryString

        assertEquals("MAX(`CMStringList`)", actual)
    }

    @Test
    fun `should support max all with CMList String`() {
        val actual: String = max(someCMStringList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMStringList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList String`() {
        val actual: String = max(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMStringList`)", actual)
    }

    @Test
    fun `should support max with CMList Boolean`() {
        val actual: String = max(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("MAX(`CMBooleanList`)", actual)
    }

    @Test
    fun `should support max all with CMList Boolean`() {
        val actual: String = max(someCMBooleanList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `CMBooleanList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList Boolean`() {
        val actual: String = max(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `CMBooleanList`)", actual)
    }
}
