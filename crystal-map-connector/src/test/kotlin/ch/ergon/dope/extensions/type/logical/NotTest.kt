package ch.ergon.dope.extensions.type.logical

import ch.ergon.dope.extension.type.logical.not
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.NotExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotTest {
    @Test
    fun `should support not with CMJsonField CMJsonField`() {
        val expression = someCMBooleanField()
        val expected = NotExpression(expression.toDopeType())

        val actual = not(expression)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
