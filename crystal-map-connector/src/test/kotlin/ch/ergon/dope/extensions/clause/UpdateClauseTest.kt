package ch.ergon.dope.extensions.clause

import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.set
import ch.ergon.dope.extension.clause.useKeys
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someUpdate
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UpdateLimitClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateUseKeysClause.Companion.UpdateUseKeysClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
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
    fun `should support update set field and value with CM`() {
        val field = someCMStringField()
        val value = someCMStringField()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType() to value.toDopeType(), parentClause = parentClause)

        val actual = parentClause.set(field to value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set field with CM`() {
        val field = someCMStringField()
        val value = someString().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(field.toDopeType() to value, parentClause = parentClause)

        val actual = parentClause.set(field to value)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set multiple fields and values with CM`() {
        val stringField = someCMStringField()
        val stringValue = someCMStringField()
        val numberField = someCMNumberField()
        val numberValue = someCMNumberField()
        val booleanField = someCMBooleanField()
        val booleanValue = someCMBooleanField()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType() to stringValue.toDopeType(),
            numberField.toDopeType() to numberValue.toDopeType(),
            booleanField.toDopeType() to booleanValue.toDopeType(),
            parentClause = parentClause,
        )

        val actual = parentClause.set(
            stringField to stringValue,
            numberField to numberValue,
            booleanField to booleanValue,
        )

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support update set multiple fields with CM`() {
        val stringField = someCMStringField()
        val stringValue = someString().toDopeType()
        val numberField = someCMNumberField()
        val numberValue = someNumber().toDopeType()
        val booleanField = someCMBooleanField()
        val booleanValue = someBoolean().toDopeType()
        val parentClause = someUpdate()
        val expected = SetClause(
            stringField.toDopeType() to stringValue,
            numberField.toDopeType() to numberValue,
            booleanField.toDopeType() to booleanValue,
            parentClause = parentClause,
        )

        val actual = parentClause.set(
            stringField to stringValue,
            numberField to numberValue,
            booleanField to booleanValue,
        )

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
