package ch.ergon.dope.resolvable.clause.joinHintTest

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.joinHint.UseIndexHint
import ch.ergon.dope.resolvable.clause.model.joinHint.UseKeysHintClass.Companion.UseKeysHint
import ch.ergon.dope.resolvable.clause.model.joinHint.useFtsIndex
import ch.ergon.dope.resolvable.clause.model.joinHint.useGsiIndex
import ch.ergon.dope.resolvable.clause.model.joinHint.useIndex
import ch.ergon.dope.resolvable.clause.model.joinHint.useKeys
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.IndexReference
import ch.ergon.dope.resolvable.fromable.IndexType.USING_FTS
import ch.ergon.dope.resolvable.fromable.IndexType.USING_GSI
import kotlin.test.Test
import kotlin.test.assertEquals

class UseKeysOrIndexHintTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support use single key hint`() {
        val expected = DopeQuery(
            queryString = "KEYS `stringField`",
            parameters = emptyMap(),
        )
        val underTest = UseKeysHint(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use array keys hint`() {
        val expected = DopeQuery(
            queryString = "KEYS `stringArrayField`",
            parameters = emptyMap(),
        )
        val underTest = UseKeysHint(someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use single key hint extension type`() {
        val key = someStringField()
        val expected = UseKeysHint(key)

        val actual = useKeys(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use single key hint extension string`() {
        val key = someString()
        val expected = UseKeysHint(key.toDopeType())

        val actual = useKeys(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array keys hint extension type`() {
        val key = someStringArrayField()
        val expected = UseKeysHint(key)

        val actual = useKeys(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array key hint extension list`() {
        val key = listOf(someString().toDopeType())
        val expected = UseKeysHint(key.toDopeType())

        val actual = useKeys(key)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use array key hint extension multiple strings`() {
        val key1 = someString()
        val key2 = someString()
        val expected = UseKeysHint(
            listOf(
                key1.toDopeType(),
                key2.toDopeType(),
            ).toDopeType(),
        )

        val actual = useKeys(key1, key2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint`() {
        val expected = DopeQuery(
            "INDEX ()",
            parameters = emptyMap(),
        )
        val underTest = UseIndexHint(IndexReference())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index hint`() {
        val expected = DopeQuery(
            "INDEX (`index`)",
            parameters = emptyMap(),
        )
        val underTest = UseIndexHint(IndexReference("index"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using gsi`() {
        val expected = DopeQuery(
            "INDEX (`index` USING GSI)",
            parameters = emptyMap(),
        )
        val underTest = UseIndexHint(IndexReference("index", USING_GSI))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index using fts`() {
        val expected = DopeQuery(
            "INDEX (`index` USING FTS)",
            parameters = emptyMap(),
        )
        val underTest = UseIndexHint(IndexReference("index", USING_FTS))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index multiple references`() {
        val expected = DopeQuery(
            "INDEX (`index`, `index2`, USING GSI, USING FTS)",
            parameters = emptyMap(),
        )
        val underTest = UseIndexHint(
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
        val expected = UseIndexHint()

        val actual = useIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint extension`() {
        val indexName = someString()
        val expected = UseIndexHint(IndexReference(indexName))

        val actual = useIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint using gsi extension`() {
        val expected = UseIndexHint(IndexReference(indexType = USING_GSI))

        val actual = useGsiIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint using gsi extension`() {
        val indexName = someString()
        val expected = UseIndexHint(IndexReference(indexName, USING_GSI))

        val actual = useGsiIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support empty use index hint using fts extension`() {
        val expected = UseIndexHint(IndexReference(indexType = USING_FTS))

        val actual = useFtsIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index hint using fts extension`() {
        val indexName = someString()
        val expected = UseIndexHint(IndexReference(indexName, USING_FTS))

        val actual = useFtsIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint extension`() {
        val indexName1 = someString()
        val indexName2 = someString()
        val expected = UseIndexHint(IndexReference(indexName1), IndexReference(indexName2))

        val actual = useIndex(indexName1).useIndex(indexName2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint using gsi extension`() {
        val indexName = someString()
        val expected = UseIndexHint(IndexReference(indexName), IndexReference(indexType = USING_GSI))

        val actual = useIndex(indexName).useGsiIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index hint using fts extension`() {
        val indexName = someString()
        val expected = UseIndexHint(IndexReference(indexName), IndexReference(indexType = USING_FTS))

        val actual = useIndex(indexName).useFtsIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
