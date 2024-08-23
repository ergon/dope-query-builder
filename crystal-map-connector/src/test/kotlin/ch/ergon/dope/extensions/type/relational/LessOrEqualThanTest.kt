package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isLessOrEqualThan
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LessOrEqualThanTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support less or equal than with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with NumberType CMFieldNumber`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with StringType CMFieldNumber`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal than with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
