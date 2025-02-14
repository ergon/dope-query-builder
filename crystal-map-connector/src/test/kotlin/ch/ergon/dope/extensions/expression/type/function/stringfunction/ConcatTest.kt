package ch.ergon.dope.extensions.expression.type.function.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.concat
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.ConcatExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Concat with CM string CM string CM string`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with type CM string CM string`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1, string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with type type CM string`() {
        val string1 = someStringField()
        val string2 = someStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1, string2, string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string type CM string`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2, string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string CM string type`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val string3 = someStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3)

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string type type`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val string3 = someStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2, string3)

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with type CM string type`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val string3 = someStringField()
        val expected = ConcatExpression(string1, string2.toDopeType(), string3)

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with string CM string CM string`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with string string CM string`() {
        val string1 = someString()
        val string2 = someString()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string string CM string`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string string string`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val string3 = someString()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string CM string string`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val string3 = someString()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with string CM string string`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val string3 = someString()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string string type`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val string3 = someStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3)

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with CM string type string`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val string3 = someString()
        val expected = ConcatExpression(string1.toDopeType(), string2, string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with string type CM string`() {
        val string1 = someString()
        val string2 = someStringField()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2, string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with type string CM string`() {
        val string1 = someStringField()
        val string2 = someString()
        val string3 = someCMStringField()
        val expected = ConcatExpression(string1, string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with string CM string type`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val string3 = someStringField()
        val expected = ConcatExpression(string1.toDopeType(), string2.toDopeType(), string3)

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Concat with type CM string string`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val string3 = someString()
        val expected = ConcatExpression(string1, string2.toDopeType(), string3.toDopeType())

        val actual = concat(string1, string2, string3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
