package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import kotlin.test.Test
import kotlin.test.assertEquals

class UnsetClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support unset clause`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` UNSET `stringField`",
        )
        val underTest = UnsetClause(
            someStringField(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple unset clauses`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` UNSET `stringField`, `numberField`",
        )
        val underTest = UnsetClause(
            someStringField(),
            someNumberField(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause extension`() {
        val stringField = someStringField()
        val parentClause = someUpdateClause()
        val expected = UnsetClause(stringField, parentClause = parentClause)

        val actual = parentClause.unset(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support unset clause extension with multiple fields`() {
        val stringField = someStringField()
        val numberField = someNumberField()
        val parentClause = someUpdateClause()
        val expected = UnsetClause(
            stringField,
            numberField,
            parentClause = parentClause,
        )

        val actual = parentClause.unset(stringField).unset(numberField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
