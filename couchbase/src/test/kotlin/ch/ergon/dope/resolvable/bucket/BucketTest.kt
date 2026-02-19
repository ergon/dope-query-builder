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
        val underTest = UnaliasedBucket("bucket", BucketScope("scope"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection bucket`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = UnaliasedBucket("bucket", BucketScope("scope", BucketCollection("collection")))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support scop bucket function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`",
        )
        val underTest = UnaliasedBucket("bucket").withScope(BucketScope("scope"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support scop bucket string function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`",
        )
        val underTest = UnaliasedBucket("bucket").withScope("scope")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support scope and collection bucket function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = UnaliasedBucket("bucket").withScopeAndCollection("scope", "collection")

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support collection bucket function`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`bucket`.`scope`.`collection`",
        )
        val underTest = UnaliasedBucket("bucket", BucketScope("scope").withCollection("collection"))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
