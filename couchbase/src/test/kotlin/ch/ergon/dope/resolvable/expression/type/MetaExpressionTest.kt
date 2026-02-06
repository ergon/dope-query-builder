package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someKeyspace
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support meta`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`)",
        )
        val underTest = MetaExpression(someKeyspace())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field cas`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`cas`",
        )
        val underTest = MetaExpression(someKeyspace())

        val actual = underTest.cas.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field expiration`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`expiration`",
        )
        val underTest = MetaExpression(someKeyspace())

        val actual = underTest.expiration.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field flags`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`flags`",
        )
        val underTest = MetaExpression(someKeyspace())

        val actual = underTest.flags.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field id`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`).`id`",
        )
        val underTest = MetaExpression(someKeyspace())

        val actual = underTest.id.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`someBucket`.`someScope`.`someCollection`).`type`",
        )
        val underTest = MetaExpression(someKeyspace("someBucket", "someScope", "someCollection"))

        val actual = underTest.type.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta field type with aliased keyspace`() {
        val expected = CouchbaseDopeQuery(
            queryString = "META(`alias`).`type`",
        )
        val underTest = MetaExpression(someKeyspace("someBucket", "someScope", "someCollection").alias("alias"))

        val actual = underTest.type.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support meta function`() {
        val expected = MetaExpression(null)

        val actual = meta()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support meta function with keyspace`() {
        val keyspace = someKeyspace()
        val expected = MetaExpression(keyspace)

        val actual = meta(keyspace)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
