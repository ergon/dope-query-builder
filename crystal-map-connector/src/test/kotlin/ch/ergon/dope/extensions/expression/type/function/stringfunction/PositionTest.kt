package ch.ergon.dope.extensions.expression.type.function.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.position
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.PositionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Position with CM string CM string`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = PositionExpression(string1.toDopeType(), string2.toDopeType())

        val actual = position(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position with CM string type`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = PositionExpression(string1.toDopeType(), string2)

        val actual = position(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position with CM string string`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = PositionExpression(string1.toDopeType(), string2.toDopeType())

        val actual = position(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position with string CM string`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = PositionExpression(string1.toDopeType(), string2.toDopeType())

        val actual = position(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position with type CM string`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = PositionExpression(string1, string2.toDopeType())

        val actual = position(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
