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
import ch.ergon.dope.resolvable.clause.model.DeleteReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.ReturningExpression
import ch.ergon.dope.resolvable.clause.model.ReturningType.ELEMENT
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.clause.model.ReturningType.VALUE
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningSingleClause
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import kotlin.test.Test
import kotlin.test.assertEquals

class ReturningClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support delete returning`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`",
        )
        val underTest = DeleteReturningClause(ReturningExpression(someStringField()), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with asterisk`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING *",
        )
        val underTest = DeleteReturningClause(AsteriskExpression(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning raw`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING RAW `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), RAW, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning value`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING VALUE `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), VALUE, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning element`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING ELEMENT `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), ELEMENT, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with multiple fields`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = DeleteReturningClause(
            ReturningExpression(someStringField()),
            ReturningExpression(someNumberField()),
            parentClause = someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with multiple fields, functions and asterisk`() {
        val expected = DopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING CONCAT(`stringField`, \"test\"), *, `numberField`",
        )
        val underTest = DeleteReturningClause(
            ReturningExpression(concat(someStringField(), "test")),
            AsteriskExpression(),
            ReturningExpression(someNumberField()),
            parentClause = someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning function`() {
        val stringField = someStringField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(ReturningExpression(stringField), parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(
            ReturningExpression(stringField),
            ReturningExpression(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringField).thenReturning(numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete returning with multiple fields, functions and asterisk extension`() {
        val stringConcatenation = concat(someStringField(), "test")
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(
            ReturningExpression(stringConcatenation),
            AsteriskExpression(),
            ReturningExpression(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringConcatenation).thenReturningAsterisk().thenReturning(numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`",
        )
        val underTest = UpdateReturningClause(ReturningExpression(someStringField()), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with asterisk`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING *",
        )
        val underTest = UpdateReturningClause(AsteriskExpression(), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning raw`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING RAW `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), RAW, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning value`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING VALUE `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), VALUE, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning element`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING ELEMENT `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), ELEMENT, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with multiple fields`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = UpdateReturningClause(
            ReturningExpression(someStringField()),
            ReturningExpression(someNumberField()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with multiple fields, functions and asterisk`() {
        val expected = DopeQuery(
            queryString = "UPDATE `someBucket` RETURNING CONCAT(`stringField`, \"test\"), *, `numberField`",
        )
        val underTest = UpdateReturningClause(
            ReturningExpression(concat(someStringField(), "test")),
            AsteriskExpression(),
            ReturningExpression(someNumberField()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning function`() {
        val stringField = someStringField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(ReturningExpression(stringField), parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(
            ReturningExpression(stringField),
            ReturningExpression(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringField).thenReturning(numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update returning with multiple fields, functions and asterisk extension`() {
        val stringConcatenation = concat(someStringField(), "test")
        val numberArrayField = someNumberArrayField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(
            ReturningExpression(stringConcatenation),
            AsteriskExpression(),
            ReturningExpression(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringConcatenation).thenReturningAsterisk().thenReturning(numberArrayField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
