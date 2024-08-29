package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.to
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support set clause`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET `stringField` = \"test\"",
            emptyMap(),
        )
        val underTest = SetClause(
            someStringField().to("test".toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with meta expiration`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET META().`expiration` = 3600",
            emptyMap(),
        )
        val underTest = SetClause(
            meta().expiration.to(3600.toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple set clauses`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET `stringField` = \"test\", META().`expiration` = 3600",
            emptyMap(),
        )
        val underTest = SetClause(
            someStringField().to("test".toDopeType()),
            meta().expiration.to(3600.toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "UPDATE `someBucket` SET `stringField` = $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = SetClause(
            someStringField().to(parameterValue.asParameter()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause extension`() {
        val stringField = someStringField()
        val stringValue = someString().toDopeType()
        val parentClause = someUpdateClause()
        val expected = SetClause(stringField.to(stringValue), parentClause = parentClause)

        val actual = parentClause.set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with number`() {
        val numberField = someNumberField()
        val numberValue = someNumber()
        val parentClause = someUpdateClause()
        val expected = SetClause(numberField.to(numberValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(numberField, numberValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with string`() {
        val stringField = someStringField()
        val stringValue = someString()
        val parentClause = someUpdateClause()
        val expected = SetClause(stringField.to(stringValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with boolean`() {
        val booleanField = someBooleanField()
        val booleanValue = someBoolean()
        val parentClause = someUpdateClause()
        val expected = SetClause(booleanField.to(booleanValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with multiple fields`() {
        val stringField = someStringField()
        val stringValue = someString().toDopeType()
        val numberField = someNumberField()
        val numberValue = someNumber().toDopeType()
        val booleanField = someBooleanField()
        val booleanValue = someBoolean().toDopeType()
        val parentClause = someUpdateClause()
        val expected = SetClause(
            stringField.to(stringValue),
            numberField.to(numberValue),
            booleanField.to(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause.set(stringField, stringValue).set(numberField, numberValue).set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with multiple fields with atomic types`() {
        val field = someStringField()
        val value = someStringField()
        val stringField = someStringField()
        val stringValue = someString()
        val numberField = someNumberField()
        val numberValue = someNumber()
        val booleanField = someBooleanField()
        val booleanValue = someBoolean()
        val parentClause = someUpdateClause()
        val expected = SetClause(
            field.to(value),
            stringField.to(stringValue.toDopeType()),
            numberField.to(numberValue.toDopeType()),
            booleanField.to(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(field, value)
            .set(stringField, stringValue)
            .set(numberField, numberValue)
            .set(booleanField, booleanValue)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
