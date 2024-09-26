package ch.ergon.dope.extensions.type.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.stringfunction.concat2
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.Concat2Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Concat2Test : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Concat2 with CM string CM string CM string`() {
        val separator = someCMStringField()
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with type CM string CM string`() {
        val separator = someStringField()
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator, string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with type type CM string`() {
        val separator = someStringField()
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator, string1, string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string type CM string`() {
        val separator = someCMStringField()
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1, string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string CM string type`() {
        val separator = someCMStringField()
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2)

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string type type`() {
        val separator = someCMStringField()
        val string1 = someStringField()
        val string2 = someStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1, string2)

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with type CM string type`() {
        val separator = someStringField()
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = Concat2Expression(separator, string1.toDopeType(), string2)

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with string CM string CM string`() {
        val separator = someString()
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with string string CM string`() {
        val separator = someString()
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string string CM string`() {
        val separator = someCMStringField()
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string string string`() {
        val separator = someCMStringField()
        val string1 = someString()
        val string2 = someString()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string CM string string`() {
        val separator = someCMStringField()
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with string CM string string`() {
        val separator = someString()
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string string type`() {
        val separator = someCMStringField()
        val string1 = someString()
        val string2 = someStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2)

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with CM string type string`() {
        val separator = someCMStringField()
        val string1 = someStringField()
        val string2 = someString()
        val expected = Concat2Expression(separator.toDopeType(), string1, string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with string type CM string`() {
        val separator = someString()
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1, string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with type string CM string`() {
        val separator = someStringField()
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = Concat2Expression(separator, string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with string CM string type`() {
        val separator = someString()
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = Concat2Expression(separator.toDopeType(), string1.toDopeType(), string2)

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat2 with type CM string string`() {
        val separator = someStringField()
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = Concat2Expression(separator, string1.toDopeType(), string2.toDopeType())

        val actual = concat2(separator, string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
