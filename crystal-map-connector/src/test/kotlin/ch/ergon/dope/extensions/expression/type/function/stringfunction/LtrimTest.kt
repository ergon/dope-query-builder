package ch.ergon.dope.extensions.expression.type.function.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.string.ltrim
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.string.LtrimExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LtrimTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Ltrim with CM string CM string`() {
        val string = someCMStringField()
        val extra = someCMStringField()
        val expected = LtrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = ltrim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Ltrim with CM string string`() {
        val string = someCMStringField()
        val extra = someString()
        val expected = LtrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = ltrim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Ltrim with string CM string`() {
        val string = someString()
        val extra = someCMStringField()
        val expected = LtrimExpression(string.toDopeType(), extra.toDopeType())

        val actual = ltrim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Ltrim with type CM string`() {
        val string = someStringField()
        val extra = someCMStringField()
        val expected = LtrimExpression(string, extra.toDopeType())

        val actual = ltrim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Ltrim with CM string type`() {
        val string = someCMStringField()
        val extra = someStringField()
        val expected = LtrimExpression(string.toDopeType(), extra)

        val actual = ltrim(string, extra)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
