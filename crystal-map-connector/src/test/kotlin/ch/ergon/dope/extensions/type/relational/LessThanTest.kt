package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isLessThan
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LessThanTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support less than with CMJsonFieldNumber CMJsonFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with CMJsonFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = LessThanExpression(left.toDopeType(), right)

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with NumberType CMJsonFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = LessThanExpression(left, right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with Number CMJsonFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with CMJsonFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with CMJsonFieldString CMJsonFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with CMJsonFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = LessThanExpression(left.toDopeType(), right)

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with StringType CMJsonFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = LessThanExpression(left, right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with String CMJsonFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less than with CMJsonFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = LessThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = LessThanExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = LessThanExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = LessThanExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support less than with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = LessThanExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isLessThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
