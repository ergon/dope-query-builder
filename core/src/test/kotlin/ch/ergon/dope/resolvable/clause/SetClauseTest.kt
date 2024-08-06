package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SetClauseTest : ParameterDependentTest {
    @Test
    fun `should support set clause`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET `stringField` = \"test\"",
            emptyMap(),
        )
        val underTest = SetClause(
            someStringField(),
            "test".toDopeType(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause with meta expiration`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET META().`expiration` = 3600",
            emptyMap(),
        )
        val underTest = SetClause(
            meta().expiration,
            3600.toDopeType(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple set clauses`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` SET `stringField` = \"test\", META().`expiration` = 3600",
            emptyMap(),
        )
        val underTest = SetClause(
            meta().expiration,
            3600.toDopeType(),
            setAssignments = mutableListOf(SetAssignment(someStringField(), "test".toDopeType())),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery()

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
            someStringField(),
            parameterValue.asParameter(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause extension`() {
        val stringField = someStringField()
        val stringValue = someString().toDopeType()
        val parentClause = someUpdateClause()
        val expected = SetClause(stringField, stringValue, parentClause = parentClause)

        val actual = parentClause.set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support set clause extension with multiple fields`() {
        val stringField = someStringField()
        val stringValue = someString().toDopeType()
        val numberField = someNumberField()
        val numberValue = someNumber().toDopeType()
        val parentClause = someUpdateClause()
        val expected = SetClause(
            stringField,
            stringValue,
            setAssignments = mutableListOf(SetAssignment(numberField, numberValue)),
            parentClause = parentClause,
        )

        val actual = parentClause.set(numberField, numberValue).set(stringField, stringValue)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
