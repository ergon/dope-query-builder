package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isMissing
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsMissingExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsMissingTest {
    @Test
    fun `should support isMissing CMFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support isMissing CMFieldString`() {
        val field = someCMStringField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support isMissing CMFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsMissingExpression(field.toDopeType())

        val actual = field.isMissing()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
