package ch.ergon.dope.extensions.expression.type.function.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.split
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.SplitExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SplitTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Split with CM string CM string`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = SplitExpression(string1.toDopeType(), string2.toDopeType())

        val actual = split(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Split with CM string string`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = SplitExpression(string1.toDopeType(), string2.toDopeType())

        val actual = split(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Split with string CM string`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = SplitExpression(string1.toDopeType(), string2.toDopeType())

        val actual = split(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Split with type CM string`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = SplitExpression(string1, string2.toDopeType())

        val actual = split(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Split with CM string type`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = SplitExpression(string1.toDopeType(), string2)

        val actual = split(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
