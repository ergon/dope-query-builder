package ch.ergon.dope.extensions.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.relational.isEqualTo
import ch.ergon.dope.extension.type.relational.isNotEqualTo
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EqualsTest {
    private lateinit var manager: DopeQueryManager

    @BeforeTest
    fun setup() {
        manager = DopeQueryManager()
    }

    @Test
    fun `should support equals to with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with NumberType CMFieldNumber`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with StringType CMFieldNumber`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldBoolean CMFieldBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldBoolean BooleanType`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with BooleanType CMFieldBoolean`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with Boolean CMFieldBoolean`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMFieldBoolean Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldNumber CMFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with NumberType CMFieldNumber`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with Number CMFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldString CMFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with StringType CMFieldNumber`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with String CMFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldBoolean CMFieldBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldBoolean BooleanType`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with BooleanType CMFieldBoolean`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with Boolean CMFieldBoolean`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMFieldBoolean Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
