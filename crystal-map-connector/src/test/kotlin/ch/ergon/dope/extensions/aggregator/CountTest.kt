package ch.ergon.dope.extensions.aggregator

import ch.ergon.dope.extension.aggregator.count
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CountTest {
    @Test
    fun `should support count with CMField Number`() {
        val actual: String = count(someCMNumberField()).toDopeQuery().queryString

        assertEquals("COUNT(`someNumberField`)", actual)
    }

    @Test
    fun `should support count with CMField String`() {
        val actual: String = count(someCMStringField()).toDopeQuery().queryString

        assertEquals("COUNT(`someStringField`)", actual)
    }

    @Test
    fun `should support count with CMField Boolean`() {
        val actual: String = count(someCMBooleanField()).toDopeQuery().queryString

        assertEquals("COUNT(`someBooleanField`)", actual)
    }

    @Test
    fun `should support count with CMList Number`() {
        val actual: String = count(someCMNumberList()).toDopeQuery().queryString

        assertEquals("COUNT(`someNumberList`)", actual)
    }

    @Test
    fun `should support count with CMList String`() {
        val actual: String = count(someCMStringList()).toDopeQuery().queryString

        assertEquals("COUNT(`someStringList`)", actual)
    }

    @Test
    fun `should support count with CMList Boolean`() {
        val actual: String = count(someCMBooleanList()).toDopeQuery().queryString

        assertEquals("COUNT(`someBooleanList`)", actual)
    }
}
