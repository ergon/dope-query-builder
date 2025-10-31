package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someKeySpace
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeysClause
import ch.ergon.dope.resolvable.expression.type.TRUE
import kotlin.test.Test
import kotlin.test.assertEquals

class NestClauseTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support standard nest with boolean condition`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * NEST `someBucket` ON TRUE",
        )
        val underTest = StandardNestOnConditionClause(
            someKeySpace(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = StandardNestOnKeysClause(
            someKeySpace(),
            keys = someStringArrayField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with on key for keyspace`() {
        val keyspace = someKeySpace()
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = StandardNestOnKeyClause(
            keyspace,
            key = someStringField(),
            keyspace = keyspace,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard nest with boolean condition function`() {
        val keyspace = someKeySpace()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = StandardNestOnConditionClause(keyspace, condition, parentClause)

        val actual = parentClause.nest(keyspace, condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard nest with on keys function`() {
        val keyspace = someKeySpace()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = StandardNestOnKeysClause(keyspace, keys, parentClause)

        val actual = parentClause.nest(keyspace, keys)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support standard nest with on key for keyspace function`() {
        val keyspace = someKeySpace()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = StandardNestOnKeyClause(keyspace, key, keyspace, parentClause)

        val actual = parentClause.nest(keyspace, key, keyspace)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner nest with boolean condition`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON TRUE",
        )
        val underTest = InnerNestOnConditionClause(
            someKeySpace(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = InnerNestOnKeysClause(
            someKeySpace(),
            keys = someStringArrayField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with on key for keyspace`() {
        val keyspace = someKeySpace()
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * INNER NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = InnerNestOnKeyClause(
            keyspace,
            key = someStringField(),
            keyspace = keyspace,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support inner nest with boolean condition function`() {
        val keyspace = someKeySpace()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = InnerNestOnConditionClause(keyspace, condition, parentClause)

        val actual = parentClause.innerNest(keyspace, condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner nest with on keys function`() {
        val keyspace = someKeySpace()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = InnerNestOnKeysClause(keyspace, keys, parentClause)

        val actual = parentClause.innerNest(keyspace, keys)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support inner nest with on key for keyspace function`() {
        val keyspace = someKeySpace()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = InnerNestOnKeyClause(keyspace, key, keyspace, parentClause)

        val actual = parentClause.innerNest(keyspace, key, keyspace)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left nest with boolean condition`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON TRUE",
        )
        val underTest = LeftNestOnConditionClause(
            someKeySpace(),
            condition = TRUE,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with on keys`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON KEYS `stringArrayField`",
        )
        val underTest = LeftNestOnKeysClause(
            someKeySpace(),
            keys = someStringArrayField(),
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with on key for keyspace`() {
        val keyspace = someKeySpace()
        val expected = CouchbaseDopeQuery(
            queryString = "SELECT * LEFT NEST `someBucket` ON KEY `stringField` FOR `someBucket`",
        )
        val underTest = LeftNestOnKeyClause(
            keyspace,
            key = someStringField(),
            keyspace = keyspace,
            parentClause = someSelectClause(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support left nest with boolean condition function`() {
        val keyspace = someKeySpace()
        val condition = TRUE
        val parentClause = someSelectClause()
        val expected = LeftNestOnConditionClause(keyspace, condition, parentClause)

        val actual = parentClause.leftNest(keyspace, condition)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left nest with on keys function`() {
        val keyspace = someKeySpace()
        val keys = someStringArrayField()
        val parentClause = someSelectClause()
        val expected = LeftNestOnKeysClause(keyspace, keys, parentClause)

        val actual = parentClause.leftNest(keyspace, keys)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support left nest with on key for keyspace function`() {
        val keyspace = someKeySpace()
        val key = someStringField()
        val parentClause = someSelectClause()
        val expected = LeftNestOnKeyClause(keyspace, key, keyspace, parentClause)

        val actual = parentClause.leftNest(keyspace, key, keyspace)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
