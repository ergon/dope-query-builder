package ch.ergon.dope.extensions.clause

import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.offset
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDelete
import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteClauseTest {
    @Test
    fun `should support delete where with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteWhereClause(field.toDopeType(), parentClause)

        val actual = parentClause.where(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support delete limit with CM`() {
        val field = someCMNumberField()
        val parentClause = someDelete()
        val expected = DeleteLimitClause(field.toDopeType(), parentClause)

        val actual = parentClause.limit(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support delete offset with CM`() {
        val field = someCMNumberField()
        val parentClause = someDelete()
        val expected = DeleteOffsetClause(field.toDopeType(), parentClause)

        val actual = parentClause.offset(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support delete returning with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = ReturningClause(field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support delete returning with multiple CM`() {
        val field1 = someCMBooleanField()
        val field2 = someCMNumberList()
        val field3 = someCMStringField()
        val parentClause = someDelete()
        val expected = ReturningClause(field1.toDopeType(), field2.toDopeType(), field3.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field1, field2, field3)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
