package ch.ergon.dope.extensions.type.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.RepeatExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class RepeatTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Repeat with CM string CM number`() {
        val string = someCMStringField()
        val repetitions = someCMNumberField()
        val expected = RepeatExpression(string.toDopeType(), repetitions.toDopeType())

        val actual = ch.ergon.dope.extension.type.stringfunction.repeat(string, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Repeat with CM string type`() {
        val string = someCMStringField()
        val repetitions = someNumberField()
        val expected = RepeatExpression(string.toDopeType(), repetitions)

        val actual = ch.ergon.dope.extension.type.stringfunction.repeat(string, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Repeat with CM string number`() {
        val string = someCMStringField()
        val repetitions = someNumber()
        val expected = RepeatExpression(string.toDopeType(), repetitions.toDopeType())

        val actual = ch.ergon.dope.extension.type.stringfunction.repeat(string, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Repeat with type CM number`() {
        val string = someStringField()
        val repetitions = someCMNumberField()
        val expected = RepeatExpression(string, repetitions.toDopeType())

        val actual = ch.ergon.dope.extension.type.stringfunction.repeat(string, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Repeat with string CM number`() {
        val string = someString()
        val repetitions = someCMNumberField()
        val expected = RepeatExpression(string.toDopeType(), repetitions.toDopeType())

        val actual = ch.ergon.dope.extension.type.stringfunction.repeat(string, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
