package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.clause.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass.Companion.KeysHint
import ch.ergon.dope.resolvable.clause.joinHint.ftsIndexHint
import ch.ergon.dope.resolvable.clause.joinHint.gsiIndexHint
import ch.ergon.dope.resolvable.clause.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.joinHint.keysHint
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.keyspace.IndexReference
import ch.ergon.dope.resolvable.keyspace.IndexType.USING_FTS
import ch.ergon.dope.resolvable.keyspace.IndexType.USING_GSI
import kotlin.test.Test
import kotlin.test.assertEquals

class KeysOrIndexHintTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support use single key hint`() {
        val expected = CouchbaseDopeQuery(
            queryString = "KEYS `stringField`",
        )
        val underTest = KeysHint(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use array keys hint`() {
        val expected = CouchbaseDopeQuery(
            queryString = "KEYS `stringArrayField`",
        )
        val underTest = KeysHint(someStringArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use single key hint extension type`() {
        val key = someStringField()
        val expected = KeysHint(key)

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use single key hint extension string`() {
        val key = someString()
        val expected = KeysHint(key.toDopeType())

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use array keys hint extension type`() {
        val key = someStringArrayField()
        val expected = KeysHint(key)

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use array keys hint extension select expression`() {
        val keys = someStringSelectRawClause()
        val expected = KeysHint(keys.asExpression())

        val actual = keysHint(keys)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use array key hint extension list`() {
        val key = listOf(someString().toDopeType())
        val expected = KeysHint(key.toDopeType())

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use array key hint extension multiple strings`() {
        val key1 = someString()
        val key2 = someString()
        val expected = KeysHint(
            listOf(
                key1.toDopeType(),
                key2.toDopeType(),
            ).toDopeType(),
        )

        val actual = keysHint(key1, key2)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support empty use index hint`() {
        val expected = CouchbaseDopeQuery(
            queryString = "INDEX ()",
        )
        val underTest = IndexHint(listOf(IndexReference()))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index hint`() {
        val expected = CouchbaseDopeQuery(
            queryString = "INDEX (`index`)",
        )
        val underTest = IndexHint(listOf(IndexReference("index")))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using gsi`() {
        val expected = CouchbaseDopeQuery(
            queryString = "INDEX (`index` USING GSI)",
        )
        val underTest = IndexHint(listOf(IndexReference("index", USING_GSI)))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using fts`() {
        val expected = CouchbaseDopeQuery(
            queryString = "INDEX (`index` USING FTS)",
        )
        val underTest = IndexHint(listOf(IndexReference("index", USING_FTS)))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index multiple references`() {
        val expected = CouchbaseDopeQuery(
            queryString = "INDEX (`index`, `index2`, USING GSI, USING FTS)",
        )
        val underTest = IndexHint(
            listOf(
                IndexReference("index"),
                IndexReference("index2"),
                IndexReference(indexType = USING_GSI),
                IndexReference(indexType = USING_FTS),
            ),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support empty use index hint extension`() {
        val expected = IndexHint()

        val actual = indexHint()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use index hint extension`() {
        val indexName = someString()
        val expected = IndexHint(listOf(IndexReference(indexName)))

        val actual = indexHint(indexName)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support empty use index hint using gsi extension`() {
        val expected = IndexHint(listOf(IndexReference(indexType = USING_GSI)))

        val actual = gsiIndexHint()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use index hint using gsi extension`() {
        val indexName = someString()
        val expected = IndexHint(listOf(IndexReference(indexName, USING_GSI)))

        val actual = gsiIndexHint(indexName)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support empty use index hint using fts extension`() {
        val expected = IndexHint(listOf(IndexReference(indexType = USING_FTS)))

        val actual = ftsIndexHint()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support use index hint using fts extension`() {
        val indexName = someString()
        val expected = IndexHint(listOf(IndexReference(indexName, USING_FTS)))

        val actual = ftsIndexHint(indexName)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support multiple use index hint extension`() {
        val indexName1 = someString()
        val indexName2 = someString()
        val expected = IndexHint(listOf(IndexReference(indexName1), IndexReference(indexName2)))

        val actual = indexHint(indexName1).indexHint(indexName2)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support multiple use index hint using gsi extension`() {
        val indexName = someString()
        val expected = IndexHint(listOf(IndexReference(indexName), IndexReference(indexType = USING_GSI)))

        val actual = indexHint(indexName).gsiIndexHint()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support multiple use index hint using fts extension`() {
        val indexName = someString()
        val expected = IndexHint(listOf(IndexReference(indexName), IndexReference(indexType = USING_FTS)))

        val actual = indexHint(indexName).ftsIndexHint()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
