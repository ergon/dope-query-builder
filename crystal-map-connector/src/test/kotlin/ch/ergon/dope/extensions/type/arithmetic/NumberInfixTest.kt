package ch.ergon.dope.extensions.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.extension.type.arithmetic.add
import ch.ergon.dope.extension.type.arithmetic.div
import ch.ergon.dope.extension.type.arithmetic.mod
import ch.ergon.dope.extension.type.arithmetic.mul
import ch.ergon.dope.extension.type.arithmetic.sub
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someNumberField
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberInfixTest {
    @Test
    fun `should support extensions for add with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMNumberField` + `CMNumberField`)",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().add(someCMNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extensions for sub with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMNumberField` - `numberField`)",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().sub(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extensions for mul with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMNumberField` * `numberField`)",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().mul(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extensions for div with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMNumberField` / `numberField`)",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().div(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support extensions for mod with CMField`() {
        val expected = DopeQuery(
            queryString = "(`CMNumberField` % `numberField`)",
            parameters = emptyMap(),
        )

        val actual = someCMNumberField().mod(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
