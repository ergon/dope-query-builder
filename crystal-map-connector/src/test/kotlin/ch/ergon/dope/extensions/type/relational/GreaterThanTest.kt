package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isGreaterThan
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
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterThanTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support greater than with CMJsonFieldNumber CMJsonFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with CMJsonFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with NumberType CMJsonFieldNumer`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with Number CMJsonFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with CMJsonFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with CMJsonFieldString CMJsonFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with CMJsonFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with StringType CMJsonFieldNumer`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with String CMJsonFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greater than with CMJsonFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = GreaterThanExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = GreaterThanExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = GreaterThanExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support greater than with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = GreaterThanExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
