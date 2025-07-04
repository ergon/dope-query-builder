package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeParameters
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
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support set clause`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET `stringField` = \"test\"",
        )
        val underTest = SetClause(
            someStringField().toNewValue("test".toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with null`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET `stringField` = NULL",
        )
        val underTest = SetClause(
            someStringField().toNewValue(NULL),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with meta expiration`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET META().`expiration` = 3600",
        )
        val underTest = SetClause(
            meta().expiration.toNewValue(3600.toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple set clauses`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET `stringField` = \"test\", META().`expiration` = 3600",
        )
        val underTest = SetClause(
            someStringField().toNewValue("test".toDopeType()),
            meta().expiration.toNewValue(3600.toDopeType()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET `stringField` = $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SetClause(
            someStringField().toNewValue(parameterValue.asParameter()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` SET `stringField` = $$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SetClause(
            someStringField().toNewValue(parameterValue.asParameter(parameterName)),
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
        val expected = SetClause(stringField.toNewValue(stringValue), parentClause = parentClause)

        val actual = parentClause.set(stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with number`() {
        val numberField = someNumberField()
        val numberValue = someNumber()
        val parentClause = someUpdateClause()
        val expected = SetClause(numberField.toNewValue(numberValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(numberField.toNewValue(numberValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with string`() {
        val stringField = someStringField()
        val stringValue = someString()
        val parentClause = someUpdateClause()
        val expected = SetClause(stringField.toNewValue(stringValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(stringField.toNewValue(stringValue))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support set clause extension with boolean`() {
        val booleanField = someBooleanField()
        val booleanValue = someBoolean()
        val parentClause = someUpdateClause()
        val expected = SetClause(booleanField.toNewValue(booleanValue.toDopeType()), parentClause = parentClause)

        val actual = parentClause.set(booleanField.toNewValue(booleanValue))

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
            stringField.toNewValue(stringValue),
            numberField.toNewValue(numberValue),
            booleanField.toNewValue(booleanValue),
            parentClause = parentClause,
        )

        val actual = parentClause
            .set(stringField.toNewValue(stringValue), numberField.toNewValue(numberValue), booleanField.toNewValue(booleanValue))

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
            field.toNewValue(value),
            stringField.toNewValue(stringValue.toDopeType()),
            numberField.toNewValue(numberValue.toDopeType()),
            booleanField.toNewValue(booleanValue.toDopeType()),
            parentClause = parentClause,
        )

        val actual = parentClause.set(
            field.toNewValue(value),
            stringField.toNewValue(stringValue),
            numberField.toNewValue(numberValue),
            booleanField.toNewValue(booleanValue),
        )

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
