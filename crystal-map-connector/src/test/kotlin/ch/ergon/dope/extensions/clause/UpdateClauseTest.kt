package ch.ergon.dope.extensions.clause

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.returningElement
import ch.ergon.dope.extension.clause.returningRaw
import ch.ergon.dope.extension.clause.returningValue
import ch.ergon.dope.extension.clause.toNewValue
import ch.ergon.dope.extension.clause.unset
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMConverterBooleanField
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someUpdate
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.clause.model.ReturningType.ELEMENT
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.clause.model.ReturningType.VALUE
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateLimitClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support update set CMJsonField number to CMJsonField number`() {
        val field = someCMNumberField()
        val value = someCMNumberField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField number to CMJsonField number`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField string to CMJsonField string`() {
        val field = someCMStringField()
        val value = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField string to CMJsonField string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField.toNewValue(numberValue), stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField boolean to CMJsonField boolean`() {
        val field = someCMBooleanField()
        val value = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField boolean to CMJsonField boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someCMBooleanField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            booleanField.toDopeType().toNewValue(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), booleanField.toNewValue(booleanValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList number to CMJsonList number`() {
        val field = someCMNumberList()
        val value = someCMNumberList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList number to CMJsonList number`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList string to CMJsonList string`() {
        val field = someCMStringList()
        val value = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList string to CMJsonList string`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField.toNewValue(numberValue), stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList boolean to CMJsonList boolean`() {
        val field = someCMBooleanList()
        val value = someCMBooleanList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList boolean to CMJsonList boolean`() {
        val booleanField = someCMBooleanList()
        val booleanValue = someCMBooleanList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            booleanField.toDopeType().toNewValue(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), booleanField.toNewValue(booleanValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField number to TypeExpression number`() {
        val field = someCMNumberField()
        val value = someNumber().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField number to null`() {
        val field = someCMNumberField()
        val value = NULL
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField number to TypeExpression number`() {
        val numberField = someCMNumberField()
        val numberValue = someNumber().toDopeType()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            numberField.toDopeType().toNewValue(numberValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField string to TypeExpression string`() {
        val field = someCMStringField()
        val value = someString().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField string to TypeExpression string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someString().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            stringField.toDopeType().toNewValue(stringValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField.toNewValue(numberValue), stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField boolean to TypeExpression boolean`() {
        val field = someCMBooleanField()
        val value = someBoolean().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField boolean to TypeExpression boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someBoolean().toDopeType()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            booleanField.toDopeType().toNewValue(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), booleanField.toNewValue(booleanValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList number to TypeExpression number`() {
        val field = someCMNumberList()
        val value = listOf(someNumber().toDopeType(), someNumber().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList number to TypeExpression number`() {
        val numberField = someCMNumberList()
        val numberValue = listOf(someNumber().toDopeType(), someNumber().toDopeType()).toDopeType()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            numberField.toDopeType().toNewValue(numberValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList string to TypeExpression string`() {
        val field = someCMStringList()
        val value = listOf(someString().toDopeType(), someString().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList string to TypeExpression string`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = listOf(someString().toDopeType(), someString().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            stringField.toDopeType().toNewValue(stringValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField.toNewValue(numberValue), stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonList boolean to TypeExpression boolean`() {
        val field = someCMBooleanList()
        val value = listOf(someBoolean().toDopeType(), someBoolean().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonList boolean to TypeExpression boolean`() {
        val booleanField = someCMBooleanList()
        val booleanValue = listOf(someBoolean().toDopeType(), someBoolean().toDopeType()).toDopeType()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            booleanField.toDopeType().toNewValue(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), booleanField.toNewValue(booleanValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField number to number`() {
        val field = someCMNumberField()
        val value = someNumber()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField number to number`() {
        val numberField = someCMNumberField()
        val numberValue = someNumber()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMConverterField number to date`() {
        val field = someCMConverterNumberField()
        val value = someDate()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toInstant().epochSecond.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMConverterField number to date`() {
        val dateField = someCMConverterNumberField()
        val dateValue = someDate()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            dateField.toDopeType().toNewValue(dateValue.toInstant().epochSecond.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), dateField.toNewValue(dateValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField string to string`() {
        val field = someCMStringField()
        val value = someString()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField string to string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someString()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().toNewValue(numberValue.toDopeType()),
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField.toNewValue(numberValue), stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMConverterField string to date`() {
        val field = someCMConverterStringField()
        val value = someDate()
        val parentClause = someUpdate()
        val expected = SetClause(
            field.toDopeType().toNewValue(value.toInstant().epochSecond.toString().toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMConverterField string to date`() {
        val dateField = someCMConverterStringField()
        val dateValue = someDate()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            dateField.toDopeType().toNewValue(dateValue.toInstant().epochSecond.toString().toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), dateField.toNewValue(dateValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMJsonField boolean to boolean`() {
        val field = someCMBooleanField()
        val value = someBoolean()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMJsonField boolean to boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someBoolean()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            booleanField.toDopeType().toNewValue(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), booleanField.toNewValue(booleanValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update set CMConverterField boolean to date`() {
        val field = someCMConverterBooleanField()
        val value = someDate()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().toNewValue(true.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field.toNewValue(value))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update additional set CMConverterField boolean to date`() {
        val dateField = someCMConverterBooleanField()
        val dateValue = someDate()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().toNewValue(stringValue.toDopeType()),
            dateField.toDopeType().toNewValue(true.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField.toNewValue(stringValue), dateField.toNewValue(dateValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update unset with CM`() {
        val stringField = someCMStringField()
        val parentClause = someUpdate()
        val expected = UnsetClause(stringField.toDopeType(), parentClause = parentClause)

        val actual = parentClause.unset(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update multiple unset with CM`() {
        val stringField = someCMStringField()
        val numberField = someCMNumberField()
        val booleanField = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UnsetClause(
            numberField.toDopeType(),
            booleanField.toDopeType(),
            stringField.toDopeType(),
            parentClause = parentClause,
        )

        val actual = parentClause.unset(numberField, booleanField, stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update where with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateWhereClause(field.toDopeType(), parentClause)

        val actual = parentClause.where(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update limit with CM`() {
        val field = someCMNumberField()
        val parentClause = someUpdate()
        val expected = UpdateLimitClause(field.toDopeType(), parentClause)

        val actual = parentClause.limit(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateReturningClause(field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning raw with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateReturningSingleClause(field.toDopeType(), returningType = RAW, parentClause = parentClause)

        val actual = parentClause.returningRaw(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning value with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateReturningSingleClause(field.toDopeType(), returningType = VALUE, parentClause = parentClause)

        val actual = parentClause.returningValue(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning element with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateReturningSingleClause(field.toDopeType(), returningType = ELEMENT, parentClause = parentClause)

        val actual = parentClause.returningElement(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning with multiple CM and asterisk`() {
        val field1 = someCMBooleanField()
        val field2 = someCMNumberList()
        val field3 = someCMStringField()
        val parentClause = someUpdate()
        val expected = UpdateReturningClause(
            field1.toDopeType(),
            field2.toDopeType(),
            asterisk(),
            field3.toDopeType(),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(field1.toDopeType(), field2.toDopeType(), asterisk(), field3.toDopeType())

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
