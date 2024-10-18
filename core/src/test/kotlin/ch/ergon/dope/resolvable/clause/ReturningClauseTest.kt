package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import kotlin.test.Test
import kotlin.test.assertEquals

class ReturningClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete returning`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`",
        )
        val underTest = DeleteReturningClause(someStringField(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with multiple fields`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = DeleteReturningClause(someStringField(), someNumberField(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning function`() {
        val stringField = someStringField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(stringField, parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(stringField, numberArrayField, parentClause = parentClause)

        val actual = parentClause.returning(stringField, numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`",
        )
        val underTest = UpdateReturningClause(someStringField(), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support returning with multiple fields`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = UpdateReturningClause(someStringField(), someNumberField(), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support returning function`() {
        val stringField = someStringField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(stringField, parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(stringField, numberArrayField, parentClause = parentClause)

        val actual = parentClause.returning(stringField, numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
