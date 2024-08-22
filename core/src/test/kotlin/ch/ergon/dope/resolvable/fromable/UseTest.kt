package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someString
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.IndexType.USING_FTS
import ch.ergon.dope.resolvable.fromable.IndexType.USING_GSI
import ch.ergon.dope.resolvable.fromable.UseKeysClass.Companion.UseKeys
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest : ParameterDependentTest {
    @Test
    fun `should support empty use index`() {
        val expected = DopeQuery(
            "`someBucket` USE INDEX ()",
            emptyMap(),
        )
        val underTest = UseIndex(someBucket())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single use index with string name`() {
        val expected = DopeQuery(
            "`someBucket` USE INDEX (`index`)",
            emptyMap(),
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index"),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiple use index with name and type mixed`() {
        val expected = DopeQuery(
            "`someBucket` USE INDEX (`index` USING GSI, USING FTS, `secondIndex`)",
            emptyMap(),
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index", USING_GSI),
            IndexReference(USING_FTS),
            IndexReference("secondIndex"),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support single use index with parameter`() {
        val expected = DopeQuery(
            "`someBucket` USE INDEX (`index`)",
            emptyMap(),
        )
        val underTest = UseIndex(
            someBucket(),
            IndexReference("index"),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use index extension function`() {
        val bucket = someBucket()
        val expected = UseIndex(bucket)

        val actual = bucket.useIndex()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support use index extension function with single index reference`() {
        val indexReference = IndexReference("index")
        val bucket = someBucket()
        val expected = UseIndex(bucket, indexReference)

        val actual = bucket.useIndex(indexReference)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support use index extension function with multiple index references`() {
        val firstIndexReference = IndexReference(USING_FTS)
        val secondIndexReference = IndexReference("index")
        val bucket = someBucket()
        val expected = UseIndex(bucket, firstIndexReference, secondIndexReference)

        val actual = bucket.useIndex(firstIndexReference, secondIndexReference)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support select single use keys`() {
        val expected = DopeQuery(
            "`someBucket` USE KEYS \"someString\"",
            emptyMap(),
        )
        val underTest = UseKeys(
            "someString".toDopeType(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select array use keys`() {
        val expected = DopeQuery(
            "`someBucket` USE KEYS [\"someString\", \"anotherString\"]",
            emptyMap(),
        )
        val underTest = UseKeys(
            listOf("someString".toDopeType(), "anotherString".toDopeType()).toDopeType(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "`someBucket` USE KEYS $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = UseKeys(
            parameterValue.asParameter(),
            someBucket(),
        )

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support use keys extension`() {
        val useKeysString = someString().toDopeType()
        val bucket = someBucket()
        val expected = UseKeys(useKeysString, bucket = bucket)

        val actual = bucket.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support use keys extension with collection`() {
        val useKeysString = listOf(someString().toDopeType())
        val bucket = someBucket()
        val expected = UseKeys(useKeysString.toDopeType(), bucket = bucket)

        val actual = bucket.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
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

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
