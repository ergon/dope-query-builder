package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.extension.type.relational.isNotNull
import ch.ergon.dope.extension.type.relational.isNull
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsNotNullExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.IsNullExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsNullTest {
    @Test
    fun `should support is Null CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNullExpression(field.toDopeType())

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is Null CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsNullExpression(field.toDopeType())

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is Null CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNullExpression(field.toDopeType())

        val actual = field.isNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Null CMJsonFieldNumber`() {
        val field = someCMNumberField()
        val expected = IsNotNullExpression(field.toDopeType())

        val actual = field.isNotNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Null CMJsonFieldString`() {
        val field = someCMStringField()
        val expected = IsNotNullExpression(field.toDopeType())

        val actual = field.isNotNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support is not Null CMJsonFieldBoolean`() {
        val field = someCMBooleanField()
        val expected = IsNotNullExpression(field.toDopeType())

        val actual = field.isNotNull()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
