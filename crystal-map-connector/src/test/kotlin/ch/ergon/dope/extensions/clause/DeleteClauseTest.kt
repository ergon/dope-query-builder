package ch.ergon.dope.extensions.clause

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.clause.limit
import ch.ergon.dope.extension.clause.offset
import ch.ergon.dope.extension.clause.returning
import ch.ergon.dope.extension.clause.returningElement
import ch.ergon.dope.extension.clause.returningRaw
import ch.ergon.dope.extension.clause.returningValue
import ch.ergon.dope.extension.clause.where
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDelete
import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.ReturningType.ELEMENT
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.clause.model.ReturningType.VALUE
import ch.ergon.dope.resolvable.expression.Asterisk
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete where with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteWhereClause(field.toDopeType(), parentClause)

        val actual = parentClause.where(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete limit with CM`() {
        val field = someCMNumberField()
        val parentClause = someDelete()
        val expected = DeleteLimitClause(field.toDopeType(), parentClause)

        val actual = parentClause.limit(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete offset with CM`() {
        val field = someCMNumberField()
        val parentClause = someDelete()
        val expected = DeleteOffsetClause(field.toDopeType(), parentClause)

        val actual = parentClause.offset(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteReturningClause(field.toDopeType(), parentClause = parentClause)

        val actual = parentClause.returning(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning raw with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteReturningSingleClause(field.toDopeType(), returningType = RAW, parentClause = parentClause)

        val actual = parentClause.returningRaw(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning value with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteReturningSingleClause(field.toDopeType(), returningType = VALUE, parentClause = parentClause)

        val actual = parentClause.returningValue(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning element with CM`() {
        val field = someCMBooleanField()
        val parentClause = someDelete()
        val expected = DeleteReturningSingleClause(field.toDopeType(), returningType = ELEMENT, parentClause = parentClause)

        val actual = parentClause.returningElement(field)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning with multiple CM and asterisk`() {
        val field1 = someCMBooleanField()
        val field2 = someCMNumberList()
        val field3 = someCMStringField()
        val parentClause = someDelete()
        val expected = DeleteReturningClause(
            field1.toDopeType(),
            field2.toDopeType(),
            Asterisk(),
            field3.toDopeType(),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(field1.toDopeType(), field2.toDopeType(), Asterisk(), field3.toDopeType())

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
