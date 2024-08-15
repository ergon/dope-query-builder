package ch.ergon.dope.extensions.clause

import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.set
import ch.ergon.dope.extension.clause.unset
import ch.ergon.dope.extension.clause.useKeys
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someUpdate
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateLimitClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateUseKeys.Companion.UpdateUseKeysClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.to
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateClauseTest {
    @Test
    fun `should support update single use keys with CM`() {
        val useKeys = someCMStringField()
        val parentClause = someUpdate()
        val expected = UpdateUseKeysClause(useKeys.toDopeType(), parentClause)

        val actual = parentClause.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update list use keys with CM`() {
        val useKeys = someCMStringList()
        val parentClause = someUpdate()
        val expected = UpdateUseKeysClause(useKeys.toDopeType(), parentClause)

        val actual = parentClause.useKeys(useKeys)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField number to CMJsonField number`() {
        val field = someCMNumberField()
        val value = someCMNumberField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField number to CMJsonField number`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            numberField.toDopeType().to(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField string to CMJsonField string`() {
        val field = someCMStringField()
        val value = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField string to CMJsonField string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().to(numberValue.toDopeType()),
            stringField.toDopeType().to(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField boolean to CMJsonField boolean`() {
        val field = someCMBooleanField()
        val value = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField boolean to CMJsonField boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someCMBooleanField()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            booleanField.toDopeType().to(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList number to CMJsonList number`() {
        val field = someCMNumberList()
        val value = someCMNumberList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList number to CMJsonList number`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            numberField.toDopeType().to(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList string to CMJsonList string`() {
        val field = someCMStringList()
        val value = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList string to CMJsonList string`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().to(numberValue.toDopeType()),
            stringField.toDopeType().to(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList boolean to CMJsonList boolean`() {
        val field = someCMBooleanList()
        val value = someCMBooleanList()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList boolean to CMJsonList boolean`() {
        val booleanField = someCMBooleanList()
        val booleanValue = someCMBooleanList()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            booleanField.toDopeType().to(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField number to TypeExpression number`() {
        val field = someCMNumberField()
        val value = someNumber().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField number to TypeExpression number`() {
        val numberField = someCMNumberField()
        val numberValue = someNumber().toDopeType()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            numberField.toDopeType().to(numberValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField string to TypeExpression string`() {
        val field = someCMStringField()
        val value = someString().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField string to TypeExpression string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someString().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().to(numberValue.toDopeType()),
            stringField.toDopeType().to(stringValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField boolean to TypeExpression boolean`() {
        val field = someCMBooleanField()
        val value = someBoolean().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField boolean to TypeExpression boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someBoolean().toDopeType()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            booleanField.toDopeType().to(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList number to TypeExpression number`() {
        val field = someCMNumberList()
        val value = listOf(someNumber().toDopeType(), someNumber().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList number to TypeExpression number`() {
        val numberField = someCMNumberList()
        val numberValue = listOf(someNumber().toDopeType(), someNumber().toDopeType()).toDopeType()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            numberField.toDopeType().to(numberValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList string to TypeExpression string`() {
        val field = someCMStringList()
        val value = listOf(someString().toDopeType(), someString().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList string to TypeExpression string`() {
        val numberField = someCMNumberList()
        val numberValue = someCMNumberList()
        val stringField = someCMStringList()
        val stringValue = listOf(someString().toDopeType(), someString().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().to(numberValue.toDopeType()),
            stringField.toDopeType().to(stringValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonList boolean to TypeExpression boolean`() {
        val field = someCMBooleanList()
        val value = listOf(someBoolean().toDopeType(), someBoolean().toDopeType()).toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonList boolean to TypeExpression boolean`() {
        val booleanField = someCMBooleanList()
        val booleanValue = listOf(someBoolean().toDopeType(), someBoolean().toDopeType()).toDopeType()
        val stringField = someCMStringList()
        val stringValue = someCMStringList()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            booleanField.toDopeType().to(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField number to number`() {
        val field = someCMNumberField()
        val value = someNumber()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField number to number`() {
        val numberField = someCMNumberField()
        val numberValue = someNumber()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            numberField.toDopeType().to(numberValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField string to string`() {
        val field = someCMStringField()
        val value = someString()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField string to string`() {
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val stringField = someCMStringField()
        val stringValue = someString()
        val parentClause = someUpdate()
        val expected = SetClause(
            numberField.toDopeType().to(numberValue.toDopeType()),
            stringField.toDopeType().to(stringValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set CMJsonField boolean to boolean`() {
        val field = someCMBooleanField()
        val value = someBoolean()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType().to(value.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(field, value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update additional set CMJsonField boolean to boolean`() {
        val booleanField = someCMBooleanField()
        val booleanValue = someBoolean()
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType().to(stringValue.toDopeType()),
            booleanField.toDopeType().to(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update unset with CM`() {
        val stringField = someCMStringField()
        val parentClause = someUpdate()
        val expected = UnsetClause(stringField.toDopeType(), parentClause = parentClause)

        val actual = parentClause.unset(stringField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
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

        val actual = parentClause.unset(numberField).unset(booleanField).unset(stringField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update where with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateWhereClause(field.toDopeType(), parentClause)

        val actual = parentClause.where(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update limit with CM`() {
        val field = someCMNumberField()
        val parentClause = someUpdate()
        val expected = UpdateLimitClause(field.toDopeType(), parentClause)

        val actual = parentClause.limit(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update returning with CM`() {
        val field = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = UpdateReturningClause(field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update returning with multiple CM`() {
        val field1 = someCMBooleanField()
        val field2 = someCMNumberList()
        val field3 = someCMStringField()
        val parentClause = someUpdate()
        val expected = UpdateReturningClause(field1.toDopeType(), field2.toDopeType(), field3.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field1, field2, field3)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
