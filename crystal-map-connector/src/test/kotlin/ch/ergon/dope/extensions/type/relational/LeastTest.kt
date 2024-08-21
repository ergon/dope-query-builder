package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.leastOf
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LeastExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LeastTest {
    @Test
    fun `should support least cm extension number field`() {
        val cmNumberField = someCMNumberField()
        val cmNumberField2 = someCMNumberField()
        val cmNumberField3 = someCMNumberField()
        val expected = LeastExpression(cmNumberField.toDopeType(), cmNumberField2.toDopeType(), cmNumberField3.toDopeType())

        val actual = leastOf(cmNumberField, cmNumberField2, cmNumberField3)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support least cm extension string field`() {
        val cmNumberField = someCMStringField()
        val cmNumberField2 = someCMStringField()
        val cmNumberField3 = someCMStringField()
        val expected = LeastExpression(cmNumberField.toDopeType(), cmNumberField2.toDopeType(), cmNumberField3.toDopeType())

        val actual = leastOf(cmNumberField, cmNumberField2, cmNumberField3)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
