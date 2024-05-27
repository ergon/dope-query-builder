package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.min
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class MinTest {
    @Test
    fun `should support min with CMField Number`() {
        val actual: String = min(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someNumberField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField Number`() {
        val actual: String = min(someCMNumberField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someNumberField`)", actual)
    }

    @Test
    fun `should support min with CMField String`() {
        val actual: String = min(someCMStringField()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someStringField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField String`() {
        val actual: String = min(someCMStringField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someStringField`)", actual)
    }

    @Test
    fun `should support min with CMField Boolean`() {
        val actual: String = min(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someBooleanField`)", actual)
    }

    @Test
    fun `should support min distinct with CMField Boolean`() {
        val actual: String = min(someCMBooleanField(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someBooleanField`)", actual)
    }

    @Test
    fun `should support min with CMList Number`() {
        val actual: String = min(someCMNumberList()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someNumberList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList Number`() {
        val actual: String = min(someCMNumberList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someNumberList`)", actual)
    }

    @Test
    fun `should support min with CMList String`() {
        val actual: String = min(someCMStringList()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someStringList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList String`() {
        val actual: String = min(someCMStringList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someStringList`)", actual)
    }

    @Test
    fun `should support min with CMList Boolean`() {
        val actual: String = min(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("MIN(ALL `someBooleanList`)", actual)
    }

    @Test
    fun `should support min distinct with CMList Boolean`() {
        val actual: String = min(someCMBooleanList(), DISTINCT).toDopeQuery().queryString

        assertEquals("MIN(DISTINCT `someBooleanList`)", actual)
    }
}
