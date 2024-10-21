package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someStringSelectRawClause
import ch.ergon.dope.resolvable.clause.model.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.model.joinHint.KeysHintClass.Companion.KeysHint
import ch.ergon.dope.resolvable.clause.model.joinHint.ftsIndexHint
import ch.ergon.dope.resolvable.clause.model.joinHint.gsiIndexHint
import ch.ergon.dope.resolvable.clause.model.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.model.joinHint.keysHint
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.IndexReference
import ch.ergon.dope.resolvable.fromable.IndexType.USING_FTS
import ch.ergon.dope.resolvable.fromable.IndexType.USING_GSI
import kotlin.test.Test
import kotlin.test.assertEquals

class KeysOrIndexHintTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support use single key hint`() {
        val expected = DopeQuery(
            queryString = "KEYS `stringField`",
        )
        val underTest = KeysHint(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use array keys hint`() {
        val expected = DopeQuery(
            queryString = "KEYS `stringArrayField`",
        )
        val underTest = KeysHint(someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use single key hint extension type`() {
        val key = someStringField()
        val expected = KeysHint(key)

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use single key hint extension string`() {
        val key = someString()
        val expected = KeysHint(key.toDopeType())

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array keys hint extension type`() {
        val key = someStringArrayField()
        val expected = KeysHint(key)

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array keys hint extension select expression`() {
        val keys = someStringSelectRawClause()
        val expected = KeysHint(keys.asExpression())

        val actual = keysHint(keys)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array key hint extension list`() {
        val key = listOf(someString().toDopeType())
        val expected = KeysHint(key.toDopeType())

        val actual = keysHint(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
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

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint`() {
        val expected = DopeQuery(
            queryString = "INDEX ()",
        )
        val underTest = IndexHint(IndexReference())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index hint`() {
        val expected = DopeQuery(
            queryString = "INDEX (`index`)",
        )
        val underTest = IndexHint(IndexReference("index"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using gsi`() {
        val expected = DopeQuery(
            queryString = "INDEX (`index` USING GSI)",
        )
        val underTest = IndexHint(IndexReference("index", USING_GSI))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using fts`() {
        val expected = DopeQuery(
            queryString = "INDEX (`index` USING FTS)",
        )
        val underTest = IndexHint(IndexReference("index", USING_FTS))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index multiple references`() {
        val expected = DopeQuery(
            queryString = "INDEX (`index`, `index2`, USING GSI, USING FTS)",
        )
        val underTest = IndexHint(
            IndexReference("index"),
            IndexReference("index2"),
            IndexReference(indexType = USING_GSI),
            IndexReference(indexType = USING_FTS),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support empty use index hint extension`() {
        val expected = IndexHint()

        val actual = indexHint()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint extension`() {
        val indexName = someString()
        val expected = IndexHint(IndexReference(indexName))

        val actual = indexHint(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint using gsi extension`() {
        val expected = IndexHint(IndexReference(indexType = USING_GSI))

        val actual = gsiIndexHint()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint using gsi extension`() {
        val indexName = someString()
        val expected = IndexHint(IndexReference(indexName, USING_GSI))

        val actual = gsiIndexHint(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint using fts extension`() {
        val expected = IndexHint(IndexReference(indexType = USING_FTS))

        val actual = ftsIndexHint()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint using fts extension`() {
        val indexName = someString()
        val expected = IndexHint(IndexReference(indexName, USING_FTS))

        val actual = ftsIndexHint(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint extension`() {
        val indexName1 = someString()
        val indexName2 = someString()
        val expected = IndexHint(IndexReference(indexName1), IndexReference(indexName2))

        val actual = indexHint(indexName1).indexHint(indexName2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint using gsi extension`() {
        val indexName = someString()
        val expected = IndexHint(IndexReference(indexName), IndexReference(indexType = USING_GSI))

        val actual = indexHint(indexName).gsiIndexHint()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint using fts extension`() {
        val indexName = someString()
        val expected = IndexHint(IndexReference(indexName), IndexReference(indexType = USING_FTS))

        val actual = indexHint(indexName).ftsIndexHint()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
