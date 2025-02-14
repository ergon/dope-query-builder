package ch.ergon.dope.extensions.expression.single.type.relational

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.relational.isEqualTo
import ch.ergon.dope.extension.expression.single.type.relational.isNotEqualTo
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someObject
import ch.ergon.dope.helper.someObjectField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.single.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class EqualsTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support equals to with CMJsonFieldNumber CMJsonFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with NumberType CMJsonFieldNumber`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with Number CMJsonFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldString CMJsonFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with StringType CMJsonFieldNumber`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with String CMJsonFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldBoolean CMJsonFieldBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldBoolean BooleanType`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with BooleanType CMJsonFieldBoolean`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with Boolean CMJsonFieldBoolean`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldBoolean Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldObject CMJsonFieldObject`() {
        val left = someCMObjectField()
        val right = someCMObjectField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldObject ObjectType`() {
        val left = someCMObjectField()
        val right = someObjectField()
        val expected = EqualsExpression(left.toDopeType(), right)

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with ObjectType CMJsonFieldObject`() {
        val left = someObjectField()
        val right = someCMObjectField()
        val expected = EqualsExpression(left, right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with Object CMJsonFieldObject`() {
        val left = someObject()
        val right = someCMObjectField()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMJsonFieldObject Object`() {
        val left = someCMObjectField()
        val right = someObject()
        val expected = EqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = EqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = EqualsExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = EqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = EqualsExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with CMConverterBooleanField date`() {
        val left = someCMConverterBooleanField()
        val right = someDate()
        val expected = EqualsExpression(left.toDopeType(), true.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support equals to with date CMConverterBooleanField`() {
        val left = someDate()
        val right = someCMConverterBooleanField()
        val expected = EqualsExpression(true.toDopeType(), right.toDopeType())

        val actual = left.isEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldNumber CMJsonFieldNumber`() {
        val left = someCMNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldNumber NumberType`() {
        val left = someCMNumberField()
        val right = someNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with NumberType CMJsonFieldNumber`() {
        val left = someNumberField()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with Number CMJsonFieldNumber`() {
        val left = someNumber()
        val right = someCMNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldNumber Number`() {
        val left = someCMNumberField()
        val right = someNumber()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMConverterNumberField date`() {
        val left = someCMConverterNumberField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with date CMConverterNumberField`() {
        val left = someDate()
        val right = someCMConverterNumberField()
        val expected = NotEqualsExpression(left.toInstant().epochSecond.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldString CMJsonFieldString`() {
        val left = someCMStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldString StringType`() {
        val left = someCMStringField()
        val right = someStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with StringType CMJsonFieldNumber`() {
        val left = someStringField()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with String CMJsonFieldString`() {
        val left = someString()
        val right = someCMStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldString String`() {
        val left = someCMStringField()
        val right = someString()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMConverterStringField date`() {
        val left = someCMConverterStringField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), right.toInstant().epochSecond.toString().toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with date CMConverterStringField`() {
        val left = someDate()
        val right = someCMConverterStringField()
        val expected = NotEqualsExpression(left.toInstant().epochSecond.toString().toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldBoolean CMJsonFieldBoolean`() {
        val left = someCMBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldBoolean BooleanType`() {
        val left = someCMBooleanField()
        val right = someBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with BooleanType CMJsonFieldBoolean`() {
        val left = someBooleanField()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with Boolean CMJsonFieldBoolean`() {
        val left = someBoolean()
        val right = someCMBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldBoolean Boolean`() {
        val left = someCMBooleanField()
        val right = someBoolean()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMConverterBooleanField date`() {
        val left = someCMConverterBooleanField()
        val right = someDate()
        val expected = NotEqualsExpression(left.toDopeType(), true.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with date CMConverterBooleanField`() {
        val left = someDate()
        val right = someCMConverterBooleanField()
        val expected = NotEqualsExpression(true.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldObject CMJsonFieldObject`() {
        val left = someCMObjectField()
        val right = someCMObjectField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldObject ObjectType`() {
        val left = someCMObjectField()
        val right = someObjectField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with ObjectType CMJsonFieldObject`() {
        val left = someObjectField()
        val right = someCMObjectField()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with Object CMJsonFieldObject`() {
        val left = someObject()
        val right = someCMObjectField()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals to with CMJsonFieldObject Object`() {
        val left = someCMObjectField()
        val right = someObject()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
