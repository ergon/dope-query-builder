package ch.ergon.dope.extensions.expression.type.function.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.trim
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.TrimExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TrimTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Trim with CM string CM string`() {
        val string = someCMStringField()
        val extra = someCMStringField()
        val expected = TrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Trim with CM string string`() {
        val string = someCMStringField()
        val extra = someString()
        val expected = TrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Trim with string CM string`() {
        val string = someString()
        val extra = someCMStringField()
        val expected = TrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Trim with type CM string`() {
        val string = someStringField()
        val extra = someCMStringField()
        val expected = TrimExpression(string, extra.toDopeType())

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Trim with CM string type`() {
        val string = someCMStringField()
        val extra = someStringField()
        val expected = TrimExpression(string.toDopeType(), extra)

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Trim with CM string char`() {
        val string = someCMStringField()
        val extra = 'a'
        val expected = TrimExpression(string.toDopeType(), extra.toString().toDopeType())

        val actual = trim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
