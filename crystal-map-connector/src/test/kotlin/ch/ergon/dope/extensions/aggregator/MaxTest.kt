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
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class MaxTest {
    @Test
    fun `should support max with CMField Number`() {
        val actual: String = max(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MAX(`someNumberField`)", actual)
    }

    @Test
    fun `should support max all with CMField Number`() {
        val actual: String = max(someCMNumberField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField Number`() {
        val actual: String = max(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support max with CMField String`() {
        val actual: String = max(someCMStringField()).toDopeQuery().queryString

        assertEquals("MAX(`someStringField`)", actual)
    }

    @Test
    fun `should support max all with CMField String`() {
        val actual: String = max(someCMStringField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someStringField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField String`() {
        val actual: String = max(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someStringField`)", actual)
    }

    @Test
    fun `should support max with CMField Boolean`() {
        val actual: String = max(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("MAX(`someBooleanField`)", actual)
    }

    @Test
    fun `should support max all with CMField Boolean`() {
        val actual: String = max(someCMBooleanField(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someBooleanField`)", actual)
    }

    @Test
    fun `should support max distinct with CMField Boolean`() {
        val actual: String = max(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someBooleanField`)", actual)
    }

    @Test
    fun `should support max with CMList Number`() {
        val actual: String = max(someCMNumberList()).toDopeQuery().queryString

        assertEquals("MAX(`someNumberList`)", actual)
    }

    @Test
    fun `should support max all with CMList Number`() {
        val actual: String = max(someCMNumberList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someNumberList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList Number`() {
        val actual: String = max(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someNumberList`)", actual)
    }

    @Test
    fun `should support max with CMList String`() {
        val actual: String = max(someCMStringList()).toDopeQuery().queryString

        assertEquals("MAX(`someStringList`)", actual)
    }

    @Test
    fun `should support max all with CMList String`() {
        val actual: String = max(someCMStringList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someStringList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList String`() {
        val actual: String = max(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someStringList`)", actual)
    }

    @Test
    fun `should support max with CMList Boolean`() {
        val actual: String = max(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("MAX(`someBooleanList`)", actual)
    }

    @Test
    fun `should support max all with CMList Boolean`() {
        val actual: String = max(someCMBooleanList(), ALL).toDopeQuery().queryString

        assertEquals("MAX(ALL `someBooleanList`)", actual)
    }

    @Test
    fun `should support max distinct with CMList Boolean`() {
        val actual: String = max(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MAX(DISTINCT `someBooleanList`)", actual)
    }
}
