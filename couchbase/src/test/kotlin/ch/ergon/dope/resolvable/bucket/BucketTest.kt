package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BucketTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support unaliased bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`",
        )
        val underTest = UnaliasedBucket("bucket")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support scope bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`",
        )
        val underTest = ScopedBucket("bucket", "scope")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = CollectionBucket("bucket", "scope", "collection")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support scope bucket function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`",
        )
        val underTest = UnaliasedBucket("bucket").useScope("scope")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection bucket function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = ScopedBucket("bucket", "scope").useCollection("collection")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection and scope bucket funciton`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = UnaliasedBucket("bucket").useScopeAndCollection("scope", "collection")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection bucket definition`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection` AS `c`",
        )
        val underTest = CollectionBucket("bucket", "scope", "collection").alias("c").asBucketDefinition()

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
