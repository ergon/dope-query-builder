package ch.ergon.dope.extensions.expression.type.relational

import ch.ergon.dope.extension.expression.type.relational.between
import ch.ergon.dope.extension.expression.type.relational.notBetween
import ch.ergon.dope.helper.DateNumberConverterInstance
import ch.ergon.dope.helper.DateStringConverterInstance
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
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
    fun `should support between with CMJsonField number and TypeExpression`() {
        val field = someCMNumberField()
        val start = someNumberField()
        val end = someCMNumberField("end")
        val expected = BetweenExpression(field.toDopeType(), start, end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMJsonField number and CMJsonField plus TypeExpression`() {
        val field = someCMNumberField()
        val start = someCMNumberField("start")
        val end = someNumberField("end")
        val expected = BetweenExpression(field.toDopeType(), start.toDopeType(), end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMJsonField number and TypeExpressions`() {
        val field = someCMNumberField()
        val start = someNumberField("start")
        val end = someNumberField("end")
        val expected = BetweenExpression(field.toDopeType(), start, end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression number and CMJsonFields`() {
        val field = someNumberField()
        val start = someCMNumberField("start")
        val end = someCMNumberField("end")
        val expected = BetweenExpression(field, start.toDopeType(), end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression number and CMJsonField plus TypeExpression`() {
        val field = someNumberField()
        val start = someCMNumberField("start")
        val end = someNumberField("end")
        val expected = BetweenExpression(field, start.toDopeType(), end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression number and TypeExpression plus CMJsonField`() {
        val field = someNumberField()
        val start = someNumberField("start")
        val end = someCMNumberField("end")
        val expected = BetweenExpression(field, start, end.toDopeType())

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
    fun `should support not between with CMJsonField number and CMJsonField plus TypeExpression`() {
        val field = someCMNumberField()
        val start = someCMNumberField("start")
        val end = someNumberField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start.toDopeType(), end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField number and TypeExpression plus CMJsonField`() {
        val field = someCMNumberField()
        val start = someNumberField("start")
        val end = someCMNumberField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start, end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField number and TypeExpressions`() {
        val field = someCMNumberField()
        val start = someNumberField("start")
        val end = someNumberField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start, end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression number and CMJsonFields`() {
        val field = someNumberField()
        val start = someCMNumberField("start")
        val end = someCMNumberField("end")
        val expected = NotBetweenExpression(field, start.toDopeType(), end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression number and CMJsonField plus TypeExpression`() {
        val field = someNumberField()
        val start = someCMNumberField("start")
        val end = someNumberField("end")
        val expected = NotBetweenExpression(field, start.toDopeType(), end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression number and TypeExpression plus CMJsonField`() {
        val field = someNumberField()
        val start = someNumberField("start")
        val end = someCMNumberField("end")
        val expected = NotBetweenExpression(field, start, end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMConverterField number`() {
        val field = someCMConverterNumberField()
        val start = someDate()
        val end = someDate()
        val expected = NotBetweenExpression(
            field.toDopeType(),
            DateNumberConverterInstance.write(start)!!.toDopeType(),
            DateNumberConverterInstance.write(end)!!.toDopeType(),
        )

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression and CMJsonField`() {
        val field = someNumberField()
        val start = someCMNumberField("start")
        val end = someNumberField("end")
        val expected = NotBetweenExpression(field, start.toDopeType(), end)

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
    fun `should support between with CMJsonField string and CMJsonField plus TypeExpression`() {
        val field = someCMStringField()
        val start = someCMStringField("start")
        val end = someStringField("end")
        val expected = BetweenExpression(field.toDopeType(), start.toDopeType(), end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMJsonField string and TypeExpression plus CMJsonField`() {
        val field = someCMStringField()
        val start = someStringField("start")
        val end = someCMStringField("end")
        val expected = BetweenExpression(field.toDopeType(), start, end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMJsonField string and TypeExpressions`() {
        val field = someCMStringField()
        val start = someStringField("start")
        val end = someStringField("end")
        val expected = BetweenExpression(field.toDopeType(), start, end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression string and CMJsonFields`() {
        val field = someStringField()
        val start = someCMStringField("start")
        val end = someCMStringField("end")
        val expected = BetweenExpression(field, start.toDopeType(), end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression string and CMJsonField plus TypeExpression`() {
        val field = someStringField()
        val start = someCMStringField("start")
        val end = someStringField("end")
        val expected = BetweenExpression(field, start.toDopeType(), end)

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with TypeExpression string and TypeExpression plus CMJsonField`() {
        val field = someStringField()
        val start = someStringField("start")
        val end = someCMStringField("end")
        val expected = BetweenExpression(field, start, end.toDopeType())

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMConverterField number`() {
        val field = someCMConverterNumberField()
        val start = someDate()
        val end = someDate()
        val expected = BetweenExpression(
            field.toDopeType(),
            DateNumberConverterInstance.write(start)!!.toDopeType(),
            DateNumberConverterInstance.write(end)!!.toDopeType(),
        )

        val actual = field.between(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between with CMConverterField string`() {
        val field = someCMConverterStringField()
        val start = someDate()
        val end = someDate()
        val expected = BetweenExpression(
            field.toDopeType(),
            DateStringConverterInstance.write(start)!!.toDopeType(),
            DateStringConverterInstance.write(end)!!.toDopeType(),
        )

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

    @Test
    fun `should support not between with CMJsonField string and CMJsonField plus TypeExpression`() {
        val field = someCMStringField()
        val start = someCMStringField("start")
        val end = someStringField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start.toDopeType(), end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField string and TypeExpression plus CMJsonField`() {
        val field = someCMStringField()
        val start = someStringField("start")
        val end = someCMStringField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start, end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMJsonField string and TypeExpressions`() {
        val field = someCMStringField()
        val start = someStringField("start")
        val end = someStringField("end")
        val expected = NotBetweenExpression(field.toDopeType(), start, end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression string and CMJsonFields`() {
        val field = someStringField()
        val start = someCMStringField("start")
        val end = someCMStringField("end")
        val expected = NotBetweenExpression(field, start.toDopeType(), end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression string and CMJsonField plus TypeExpression`() {
        val field = someStringField()
        val start = someCMStringField("start")
        val end = someStringField("end")
        val expected = NotBetweenExpression(field, start.toDopeType(), end)

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with TypeExpression string and TypeExpression plus CMJsonField`() {
        val field = someStringField()
        val start = someStringField("start")
        val end = someCMStringField("end")
        val expected = NotBetweenExpression(field, start, end.toDopeType())

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not between with CMConverterField string`() {
        val field = someCMConverterStringField()
        val start = someDate()
        val end = someDate()
        val expected = NotBetweenExpression(
            field.toDopeType(),
            DateStringConverterInstance.write(start)!!.toDopeType(),
            DateStringConverterInstance.write(end)!!.toDopeType(),
        )

        val actual = field.notBetween(start, end)

        assertEquals(expected, actual)
    }
}
