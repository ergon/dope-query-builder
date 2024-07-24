package ch.ergon.dope.extensions.type.typefunction

import ch.ergon.dope.extension.type.typefunction.isArray
import ch.ergon.dope.extension.type.typefunction.isAtom
import ch.ergon.dope.extension.type.typefunction.isBoolean
import ch.ergon.dope.extension.type.typefunction.isNumber
import ch.ergon.dope.extension.type.typefunction.isString
import ch.ergon.dope.extension.type.typefunction.toArray
import ch.ergon.dope.extension.type.typefunction.toBoolean
import ch.ergon.dope.extension.type.typefunction.toNumber
import ch.ergon.dope.extension.type.typefunction.toString
import ch.ergon.dope.extension.type.typefunction.typeOf
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionTest {
    @Test
    fun `should support isArray`() {
        val expected = "ISARRAY(`CMNumberField`)"

        val actual = isArray(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support isAtom`() {
        val expected = "ISATOM(`CMNumberField`)"

        val actual = isAtom(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support isBoolean`() {
        val expected = "ISBOOLEAN(`CMNumberField`)"

        val actual = isBoolean(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support isNumber`() {
        val expected = "ISNUMBER(`CMNumberField`)"

        val actual = isNumber(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support isString`() {
        val expected = "ISSTRING(`CMNumberField`)"

        val actual = isString(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toArray`() {
        val expected = "TOARRAY(`CMNumberField`)"

        val actual = toArray(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toBoolean`() {
        val expected = "TOBOOLEAN(`CMNumberField`)"

        val actual = toBoolean(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toNumber`() {
        val expected = "TONUMBER(`CMNumberField`)"

        val actual = toNumber(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toNumber with string expression`() {
        val expected = "TONUMBER(`CMStringField`, \"abc\")"

        val actual = toNumber(someCMStringField(), "abc").toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toString`() {
        val expected = "TOSTRING(`CMNumberField`)"

        val actual = toString(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support typeOf`() {
        val expected = "TYPE(`CMNumberField`)"

        val actual = typeOf(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }
}
