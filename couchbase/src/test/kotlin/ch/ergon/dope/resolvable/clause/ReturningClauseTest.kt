package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.ReturningType.ELEMENT
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.clause.model.ReturningType.VALUE
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningSingleClause
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import kotlin.test.Test
import kotlin.test.assertEquals

class ReturningClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support delete returning`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`",
        )
        val underTest = DeleteReturningClause(someStringField(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with asterisk`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING *",
        )
        val underTest = DeleteReturningClause(asterisk(), parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning raw`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING RAW `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), RAW, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning value`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING VALUE `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), VALUE, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning element`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING ELEMENT `stringField`",
        )
        val underTest = DeleteReturningSingleClause(someStringField(), ELEMENT, parentClause = someDeleteClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with multiple fields`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = DeleteReturningClause(
            someStringField(),
            listOf(someNumberField()),
            parentClause = someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with multiple fields, functions and asterisk`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING CONCAT(`stringField`, \"test\"), *, `numberField`",
        )
        val underTest = DeleteReturningClause(
            concat(someStringField(), "test"),
            listOf(
                asterisk(),
                someNumberField(),
            ),
            parentClause = someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning with aliased subquery`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DELETE FROM `someBucket` RETURNING (SELECT * FROM `someBucket`) AS `sub`",
        )
        val underTest = DeleteReturningClause(
            QueryBuilder.selectAsterisk().from(someBucket()).alias("sub"),
            parentClause = someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete returning function`() {
        val stringField = someStringField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(stringField, parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support delete returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(
            stringField,
            listOf(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringField, numberArrayField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support delete returning with multiple fields, functions and asterisk extension`() {
        val stringConcatenation = concat(someStringField(), "test")
        val asterisk = asterisk()
        val numberArrayField = someNumberArrayField()
        val parentClause = someDeleteClause()
        val expected = DeleteReturningClause(
            stringConcatenation,
            listOf(
                asterisk,
                numberArrayField,
            ),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringConcatenation, asterisk, numberArrayField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support update returning`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`",
        )
        val underTest = UpdateReturningClause(someStringField(), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with asterisk`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING *",
        )
        val underTest = UpdateReturningClause(asterisk(), parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning raw`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING RAW `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), RAW, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning value`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING VALUE `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), VALUE, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning element`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING ELEMENT `stringField`",
        )
        val underTest = UpdateReturningSingleClause(someStringField(), ELEMENT, parentClause = someUpdateClause())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with multiple fields`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING `stringField`, `numberField`",
        )
        val underTest = UpdateReturningClause(
            someStringField(),
            listOf(someNumberField()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning with multiple fields, functions and asterisk`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` RETURNING CONCAT(`stringField`, \"test\"), *, `numberField`",
        )
        val underTest = UpdateReturningClause(
            concat(someStringField(), "test"),
            listOf(
                asterisk(),
                someNumberField(),
            ),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update returning function`() {
        val stringField = someStringField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(stringField, parentClause = parentClause)

        val actual = parentClause.returning(stringField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support update returning function with multiple fields`() {
        val stringField = someStringField()
        val numberArrayField = someNumberArrayField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(
            stringField,
            listOf(numberArrayField),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringField, numberArrayField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support update returning with multiple fields, functions and asterisk extension`() {
        val stringConcatenation = concat(someStringField(), "test")
        val asterisk = asterisk()
        val numberArrayField = someNumberArrayField()
        val parentClause = someUpdateClause()
        val expected = UpdateReturningClause(
            stringConcatenation,
            listOf(
                asterisk,
                numberArrayField,
            ),
            parentClause = parentClause,
        )

        val actual = parentClause.returning(stringConcatenation, asterisk, numberArrayField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
