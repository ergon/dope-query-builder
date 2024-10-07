package ch.ergon.dope.extensions.type.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.stringfunction.position1
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.Position1Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class Position1Test : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Position1 with CM string CM string`() {
        val string1 = someCMStringField()
        val string2 = someCMStringField()
        val expected = Position1Expression(string1.toDopeType(), string2.toDopeType())

        val actual = position1(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position1 with CM string type`() {
        val string1 = someCMStringField()
        val string2 = someStringField()
        val expected = Position1Expression(string1.toDopeType(), string2)

        val actual = position1(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position1 with CM string string`() {
        val string1 = someCMStringField()
        val string2 = someString()
        val expected = Position1Expression(string1.toDopeType(), string2.toDopeType())

        val actual = position1(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position1 with string CM string`() {
        val string1 = someString()
        val string2 = someCMStringField()
        val expected = Position1Expression(string1.toDopeType(), string2.toDopeType())

        val actual = position1(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Position1 with type CM string`() {
        val string1 = someStringField()
        val string2 = someCMStringField()
        val expected = Position1Expression(string1, string2.toDopeType())

        val actual = position1(string1, string2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}