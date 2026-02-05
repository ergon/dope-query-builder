package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import kotlin.test.Test
import kotlin.test.assertEquals

class UnsetClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support unset clause`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` UNSET `stringField`",
        )
        val underTest = UnsetClause(
            someStringField(),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple unset clauses`() {
        val expected = CouchbaseDopeQuery(
            queryString = "UPDATE `someBucket` UNSET `stringField`, `numberField`",
        )
        val underTest = UnsetClause(
            someStringField(),
            listOf(someNumberField()),
            parentClause = someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support set clause extension`() {
        val stringField = someStringField()
        val parentClause = someUpdateClause()
        val expected = UnsetClause(stringField, parentClause = parentClause)

        val actual = parentClause.unset(stringField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support unset clause extension with multiple fields`() {
        val stringField = someStringField()
        val numberField = someNumberField()
        val parentClause = someUpdateClause()
        val expected = UnsetClause(
            stringField,
            listOf(numberField),
            parentClause = parentClause,
        )

        val actual = parentClause.unset(stringField, numberField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
