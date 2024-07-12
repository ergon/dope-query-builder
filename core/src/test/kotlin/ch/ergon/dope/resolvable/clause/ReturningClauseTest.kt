package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReturningClauseTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support returning`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` RETURNING `stringField`",
            emptyMap(),
        )
        val underTest = ReturningClause(someStringField(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support returning with multiple fields`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`",
            emptyMap(),
        )
        val underTest = ReturningClause(someStringField(), someNumberField(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support returning function`() {
        val stringField = someStringField()
        val parentClause = someDeleteClause()
        val expected = ReturningClause(stringField, parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = ReturningClause(stringField, numberArrayField, parentClause = parentClause)

        val actual = parentClause.returning(stringField, numberArrayField)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
