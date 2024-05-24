package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.min
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class MinTest {
    @Test
    fun `should support min with CMField Number`() {
        val actual: String = min(someCMNumberField()).toDopeQuery().queryString

        assertEquals("MIN(`someNumberField`)", actual)
    }

    @Test
    fun `should support min with CMField String`() {
        val actual: String = min(someCMStringField()).toDopeQuery().queryString

        assertEquals("MIN(`someStringField`)", actual)
    }

    @Test
    fun `should support min with CMField Boolean`() {
        val actual: String = min(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("MIN(`someBooleanField`)", actual)
    }

    @Test
    fun `should support min with CMList Number`() {
        val actual: String = min(someCMNumberList()).toDopeQuery().queryString

        assertEquals("MIN(`someNumberList`)", actual)
    }

    @Test
    fun `should support min with CMList String`() {
        val actual: String = min(someCMStringList()).toDopeQuery().queryString

        assertEquals("MIN(`someStringList`)", actual)
    }

    @Test
    fun `should support min with CMList Boolean`() {
        val actual: String = min(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("MIN(`someBooleanList`)", actual)
    }
}
