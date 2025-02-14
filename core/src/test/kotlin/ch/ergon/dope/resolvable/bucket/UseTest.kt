package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.bucket.IndexType.USING_FTS
import ch.ergon.dope.resolvable.bucket.IndexType.USING_GSI
import ch.ergon.dope.resolvable.bucket.UseKeysClass.Companion.UseKeys
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support empty use index`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE INDEX ()",
        )
        val underTest = UseIndex(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single use index with string name`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE INDEX (`index`)",
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple use index with name and type mixed`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE INDEX (`index` USING GSI, USING FTS, `secondIndex`)",
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index", USING_GSI),
            IndexReference(indexType = USING_FTS),
            IndexReference("secondIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single use index with parameter`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE INDEX (`index`)",
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index extension function without index name`() {
        val bucket = someBucket()
        val expected = UseIndex(bucket)

        val actual = bucket.useIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use index extension function`() {
        val indexName = "index"
        val bucket = someBucket()
        val expected = UseIndex(bucket, IndexReference(indexName))

        val actual = bucket.useIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use gsi index extension function`() {
        val indexName = "index"
        val bucket = someBucket()
        val expected = UseIndex(bucket, IndexReference(indexName, USING_GSI))

        val actual = bucket.useGsiIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use fts index extension function`() {
        val indexName = "index"
        val bucket = someBucket()
        val expected = UseIndex(bucket, IndexReference(indexName, USING_FTS))

        val actual = bucket.useFtsIndex(indexName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple use index extension function`() {
        val indexName = "index"
        val indexName2 = "index"
        val bucket = someBucket()
        val expected = UseIndex(bucket, IndexReference(indexName), IndexReference(indexName2))

        val actual = bucket.useIndex(indexName).useIndex(indexName2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support multiple different use index extension function`() {
        val indexName = "index"
        val indexName2 = "index"
        val bucket = someBucket()
        val expected = UseIndex(
            bucket,
            IndexReference(indexName, USING_GSI),
            IndexReference(indexName2),
            IndexReference(indexType = USING_FTS),
            IndexReference(indexType = USING_GSI),
        )

        val actual = bucket.useGsiIndex(indexName).useIndex(indexName2).useFtsIndex().useGsiIndex()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support select single use keys`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE KEYS \"someString\"",
        )
        val underTest = UseKeys(
            "someString".toDopeType(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select array use keys`() {
        val expected = DopeQuery(
            queryString = "`someBucket` USE KEYS [\"someString\", \"anotherString\"]",
        )
        val underTest = UseKeys(
            listOf("someString".toDopeType(), "anotherString".toDopeType()).toDopeType(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            queryString = "`someBucket` USE KEYS $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = UseKeys(
            parameterValue.asParameter(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys extension`() {
        val useKeysString = someString().toDopeType()
        val bucket = someBucket()
        val expected = UseKeys(useKeysString, bucket = bucket)

        val actual = bucket.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use keys extension with collection`() {
        val useKeysString = listOf(someString().toDopeType())
        val bucket = someBucket()
        val expected = UseKeys(useKeysString.toDopeType(), bucket = bucket)

        val actual = bucket.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support use keys extension with strings`() {
        val useKeysString1 = someString()
        val useKeysString2 = someString()
        val useKeysString3 = someString()
        val bucket = someBucket()
        val expected = UseKeys(
            listOf(useKeysString1.toDopeType(), useKeysString2.toDopeType(), useKeysString3.toDopeType()).toDopeType(),
            bucket = bucket,
        )

        val actual = bucket.useKeys(useKeysString1, useKeysString2, useKeysString3)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
