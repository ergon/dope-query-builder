package ch.ergon.dope.extensions

import ch.ergon.dope.extension.expression.asterisk
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class AsteriskTest {
    @Test
    fun `should support asterisk extension on cm object field`() {
        val cmObjectField = someCMObjectField()
        val expected = Asterisk(cmObjectField.toDopeType())

        val actual = cmObjectField.asterisk()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk function with cm object field`() {
        val cmObjectField = someCMObjectField()
        val expected = Asterisk(cmObjectField.toDopeType())

        val actual = asterisk(cmObjectField)

        assertEquals(expected, actual)
    }
}
