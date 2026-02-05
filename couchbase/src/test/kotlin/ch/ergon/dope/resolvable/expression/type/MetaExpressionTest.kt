package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support meta`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`)",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`cas`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.cas.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`expiration`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.expiration.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`flags`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.flags.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`id`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.id.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`type`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.type.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field keyspace`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`keyspace`",
        )
        val underTest = MetaExpression(someBucket())

        val actual = underTest.keyspace.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta function`() {
        val expected = MetaExpression(null)

        val actual = meta()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support meta function with bucket`() {
        val bucket = someBucket()
        val expected = MetaExpression(bucket)

        val actual = meta(bucket)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
