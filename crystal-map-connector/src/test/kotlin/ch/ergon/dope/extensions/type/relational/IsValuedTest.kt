package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isNotValued
import ch.ergon.dope.extension.type.relational.isValued
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsNotValuedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsValuedExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsValuedTest {
    @Test
    fun `should support  Valued CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support  Valued CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support  Valued CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsValuedExpression(field.toDopeType())

        val actual = field.isValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Valued CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Valued CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Valued CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNotValuedExpression(field.toDopeType())

        val actual = field.isNotValued()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
