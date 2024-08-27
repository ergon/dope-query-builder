package ch.ergon.dope.extensions.type.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.conditional.nvl2
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBooleanFieldList
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberFieldList
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringFieldList
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Nvl2Test : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nvl2 CMType CMNumberField CMNumberField`() {
        val expression = someCMStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMStringField CMStringField`() {
        val expression = someCMNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMBooleanField CMBooleanField`() {
        val expression = someCMStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMNumberList CMNumberList`() {
        val expression = someCMStringField()
        val valueIfExists = someCMNumberList()
        val valueIfNotExists = someCMNumberList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMStringList CMStringList`() {
        val expression = someCMStringField()
        val valueIfExists = someCMStringList()
        val valueIfNotExists = someCMStringList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMBooleanList CMBooleanList`() {
        val expression = someCMStringField()
        val valueIfExists = someCMBooleanList()
        val valueIfNotExists = someCMBooleanList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMNumberField unaliased number`() {
        val expression = someCMStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someNumberField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMStringField unaliased string`() {
        val expression = someCMNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someStringField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMBooleanField unaliased boolean`() {
        val expression = someCMStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someBooleanField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMNumberList unaliased number list`() {
        val expression = someCMStringField()
        val valueIfExists = someCMNumberList()
        val valueIfNotExists = someNumberFieldList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMStringList unaliased string list`() {
        val expression = someCMStringField()
        val valueIfExists = someCMStringList()
        val valueIfNotExists = someStringFieldList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMBooleanList unaliased boolean list`() {
        val expression = someCMStringField()
        val valueIfExists = someCMBooleanList()
        val valueIfNotExists = someBooleanFieldList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMNumberField number`() {
        val expression = someCMStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someNumber()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMStringField string`() {
        val expression = someCMNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someString()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType CMBooleanField boolean`() {
        val expression = someCMStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someBoolean()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased number CMNumberField`() {
        val expression = someCMStringField()
        val valueIfExists = someNumberField()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased string CMStringField`() {
        val expression = someCMNumberField()
        val valueIfExists = someStringField()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased boolean CMBooleanField`() {
        val expression = someCMStringField()
        val valueIfExists = someBooleanField()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased number list CMNumberList`() {
        val expression = someCMStringField()
        val valueIfExists = someNumberFieldList()
        val valueIfNotExists = someCMNumberList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased string list CMStringList`() {
        val expression = someCMStringField()
        val valueIfExists = someStringFieldList()
        val valueIfNotExists = someCMStringList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType unaliased boolean list CMBooleanList`() {
        val expression = someCMStringField()
        val valueIfExists = someBooleanFieldList()
        val valueIfNotExists = someCMBooleanList()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType number CMNumberField`() {
        val expression = someCMStringField()
        val valueIfExists = someNumber()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType string CMStringField`() {
        val expression = someCMNumberField()
        val valueIfExists = someString()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType boolean CMBooleanField`() {
        val expression = someCMStringField()
        val valueIfExists = someBoolean()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType number number`() {
        val expression = someCMStringField()
        val valueIfExists = someNumber()
        val valueIfNotExists = someNumber()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType string string`() {
        val expression = someCMNumberField()
        val valueIfExists = someString()
        val valueIfNotExists = someString()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 CMType boolean boolean`() {
        val expression = someCMStringField()
        val valueIfExists = someBoolean()
        val valueIfNotExists = someBoolean()
        val expected = Nvl2Expression(expression.toDopeType(), valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMNumberField CMNumberField`() {
        val expression = someStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMStringField CMStringField`() {
        val expression = someNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMBooleanField CMBooleanField`() {
        val expression = someStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMNumberList CMNumberList`() {
        val expression = someStringField()
        val valueIfExists = someCMNumberList()
        val valueIfNotExists = someCMNumberList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMStringList CMStringList`() {
        val expression = someStringField()
        val valueIfExists = someCMStringList()
        val valueIfNotExists = someCMStringList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMBooleanList CMBooleanList`() {
        val expression = someStringField()
        val valueIfExists = someCMBooleanList()
        val valueIfNotExists = someCMBooleanList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMNumberField unaliased number`() {
        val expression = someStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someNumberField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMStringField unaliased string`() {
        val expression = someNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someStringField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMBooleanField unaliased boolean`() {
        val expression = someStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someBooleanField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMNumberList unaliased number list`() {
        val expression = someStringField()
        val valueIfExists = someCMNumberList()
        val valueIfNotExists = someNumberFieldList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMStringList unaliased string list`() {
        val expression = someStringField()
        val valueIfExists = someCMStringList()
        val valueIfNotExists = someStringFieldList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMBooleanList unaliased boolean list`() {
        val expression = someStringField()
        val valueIfExists = someCMBooleanList()
        val valueIfNotExists = someBooleanFieldList()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists)

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMNumberField number`() {
        val expression = someStringField()
        val valueIfExists = someCMNumberField()
        val valueIfNotExists = someNumber()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMStringField string`() {
        val expression = someNumberField()
        val valueIfExists = someCMStringField()
        val valueIfNotExists = someString()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased CMBooleanField boolean`() {
        val expression = someStringField()
        val valueIfExists = someCMBooleanField()
        val valueIfNotExists = someBoolean()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased number CMNumberField`() {
        val expression = someStringField()
        val valueIfExists = someNumberField()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased string CMStringField`() {
        val expression = someNumberField()
        val valueIfExists = someStringField()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased boolean CMBooleanField`() {
        val expression = someStringField()
        val valueIfExists = someBooleanField()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased number list CMNumberList`() {
        val expression = someStringField()
        val valueIfExists = someNumberFieldList()
        val valueIfNotExists = someCMNumberList()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased string list CMStringList`() {
        val expression = someStringField()
        val valueIfExists = someStringFieldList()
        val valueIfNotExists = someCMStringList()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased unaliased boolean list CMBooleanList`() {
        val expression = someStringField()
        val valueIfExists = someBooleanFieldList()
        val valueIfNotExists = someCMBooleanList()
        val expected = Nvl2Expression(expression, valueIfExists, valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased number CMNumberField`() {
        val expression = someStringField()
        val valueIfExists = someNumber()
        val valueIfNotExists = someCMNumberField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased string CMStringField`() {
        val expression = someNumberField()
        val valueIfExists = someString()
        val valueIfNotExists = someCMStringField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nvl2 unaliased boolean CMBooleanField`() {
        val expression = someStringField()
        val valueIfExists = someBoolean()
        val valueIfNotExists = someCMBooleanField()
        val expected = Nvl2Expression(expression, valueIfExists.toDopeType(), valueIfNotExists.toDopeType())

        val actual = nvl2(expression, valueIfExists, valueIfNotExists)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
