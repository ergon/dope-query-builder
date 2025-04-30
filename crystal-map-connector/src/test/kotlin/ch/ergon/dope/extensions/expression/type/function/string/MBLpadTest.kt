package ch.ergon.dope.extensions.expression.type.function.string

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.mbLpad
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.MBLpadExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBLpadTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MBLpad with CM string CM number CM string`() {
        val string = someCMStringField()
        val size = someCMNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string CM number type`() {
        val string = someCMStringField()
        val size = someCMNumberField()
        val char = someStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char)

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string CM number string`() {
        val string = someCMStringField()
        val size = someCMNumberField()
        val char = someString()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string type string`() {
        val string = someCMStringField()
        val size = someNumberField()
        val char = someString()
        val expected = MBLpadExpression(string.toDopeType(), size, char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string type CM string`() {
        val string = someCMStringField()
        val size = someNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size, char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string type type`() {
        val string = someCMStringField()
        val size = someNumberField()
        val char = someStringField()
        val expected = MBLpadExpression(string.toDopeType(), size, char)

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string number type`() {
        val string = someCMStringField()
        val size = someNumber()
        val char = someStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char)

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string number string`() {
        val string = someCMStringField()
        val size = someNumber()
        val char = someString()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with CM string number CM string`() {
        val string = someCMStringField()
        val size = someNumber()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with type CM number CM string`() {
        val string = someStringField()
        val size = someCMNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string, size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with type CM number string`() {
        val string = someStringField()
        val size = someCMNumberField()
        val char = someString()
        val expected = MBLpadExpression(string, size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with type CM number type`() {
        val string = someStringField()
        val size = someCMNumberField()
        val char = someStringField()
        val expected = MBLpadExpression(string, size.toDopeType(), char)

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with type type CM string`() {
        val string = someStringField()
        val size = someNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string, size, char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with type type string`() {
        val string = someStringField()
        val size = someNumberField()
        val char = someString()
        val expected = MBLpadExpression(string, size, char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with string CM number CM string`() {
        val string = someString()
        val size = someCMNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with string CM number string`() {
        val string = someString()
        val size = someCMNumberField()
        val char = someString()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with string CM number type`() {
        val string = someString()
        val size = someCMNumberField()
        val char = someStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char)

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with string number CM string`() {
        val string = someString()
        val size = someNumber()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size.toDopeType(), char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLpad with string type CM string`() {
        val string = someString()
        val size = someNumberField()
        val char = someCMStringField()
        val expected = MBLpadExpression(string.toDopeType(), size, char.toDopeType())

        val actual = mbLpad(string, size, char)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
