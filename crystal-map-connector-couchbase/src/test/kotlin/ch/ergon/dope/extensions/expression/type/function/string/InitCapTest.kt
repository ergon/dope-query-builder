package ch.ergon.dope.extensions.expression.type.function.string

import ch.ergon.dope.extension.expression.type.function.string.initCap
import ch.ergon.dope.extension.expression.type.function.string.title
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.type.function.string.InitCapExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TitleExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InitCapTest {
    @Test
    fun `should support InitCap with CM string`() {
        val string = someCMStringField()
        val expected = InitCapExpression(string.toDopeType())

        val actual = string.initCap()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Title with CM string`() {
        val string = someCMStringField()
        val expected = TitleExpression(string.toDopeType())

        val actual = string.title()

        assertEquals(expected, actual)
    }
}
