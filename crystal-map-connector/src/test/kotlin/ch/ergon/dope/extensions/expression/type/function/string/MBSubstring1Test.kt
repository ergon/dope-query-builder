package ch.ergon.dope.extensions.expression.type.function.string

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.mbSubstring1
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstring1Expression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class MBSubstring1Test : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support MBSubstring1 with CM string CM number CM number`() {
        val string = someCMStringField()
        val startPos = someCMNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string type type`() {
        val string = someCMStringField()
        val startPos = someNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos, length)

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with type CM number type`() {
        val string = someStringField()
        val startPos = someCMNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(string, startPos.toDopeType(), length)

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with type type CM number`() {
        val string = someStringField()
        val startPos = someNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string, startPos, length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string CM number type`() {
        val string = someCMStringField()
        val startPos = someCMNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length)

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string type CM number`() {
        val string = someCMStringField()
        val startPos = someNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos, length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with type CM number CM number`() {
        val string = someStringField()
        val startPos = someCMNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string, startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string type int`() {
        val string = someCMStringField()
        val startPos = someNumberField()
        val length = 1
        val expected = MBSubstring1Expression(string.toDopeType(), startPos, length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with type CM number int`() {
        val string = someStringField()
        val startPos = someCMNumberField()
        val length = 1
        val expected = MBSubstring1Expression(string, startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string CM number int`() {
        val string = someCMStringField()
        val startPos = someCMNumberField()
        val length = 1
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with type int CM number`() {
        val string = someStringField()
        val startPos = 1
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string, startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string int type`() {
        val string = someCMStringField()
        val startPos = 1
        val length = someNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length)

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string int CM number`() {
        val string = someCMStringField()
        val startPos = 1
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with CM string int int`() {
        val string = someCMStringField()
        val startPos = 1
        val length = 1
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with string CM number type`() {
        val string = someString()
        val startPos = someCMNumberField()
        val length = someNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length)

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with string type CM number`() {
        val string = someString()
        val startPos = someNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos, length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with string CM number CM number`() {
        val string = someString()
        val startPos = someCMNumberField()
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with string CM number int`() {
        val string = someString()
        val startPos = someCMNumberField()
        val length = 1
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBSubstring1 with string int CM number`() {
        val string = someString()
        val startPos = 1
        val length = someCMNumberField()
        val expected = MBSubstring1Expression(string.toDopeType(), startPos.toDopeType(), length.toDopeType())

        val actual = mbSubstring1(string, startPos, length)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
