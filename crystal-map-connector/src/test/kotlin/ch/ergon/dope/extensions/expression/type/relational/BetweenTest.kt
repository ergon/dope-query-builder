package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.between
import ch.ergon.dope.extension.expression.type.relational.notBetween
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class BetweenTest {
    @Test
    fun `should support between with CMJsonField number`() {
        val field = someCMNumberField()
        val start = someCMNumberField("start")
        val end = someCMNumberField("end")
        val expected = BetweenExpression(field.toDopeType(), start.toDopeType(), end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField number`() {
        val field = someCMNumberField()
        val start = someCMNumberField("start")
        val end = someCMNumberField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start.toDopeType(), end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMJsonField string`() {
        val field = someCMStringField()
        val start = someCMStringField("start")
        val end = someCMStringField("end")
        val expected = BetweenExpression(field.toDopeType(), start.toDopeType(), end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField string`() {
        val field = someCMStringField()
        val start = someCMStringField("start")
        val end = someCMStringField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start.toDopeType(), end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }
}
