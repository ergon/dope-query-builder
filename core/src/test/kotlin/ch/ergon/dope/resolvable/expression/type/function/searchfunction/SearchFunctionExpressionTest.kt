package ch.ergon.dope.resolvable.expression.type.function.searchfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.search.SearchBucketObjectFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchBucketStringFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFieldObjectFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFieldStringFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchMetaFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchScoreFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearch
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearchMeta
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearchScore
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchFunctionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support search string function expression on field`() {
        val field = someStringField(bucket = someBucket())
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, \"someString\")",
        )
        val underTest = SearchFieldStringFunctionExpression(field, "someString")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function expression on field with options`() {
        val field = someStringField(bucket = someBucket())
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, \"+someString\", {\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFieldStringFunctionExpression(
            field,
            searchQuery = "+someString",
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function extension on field`() {
        val field = someStringField(bucket = someBucket())
        val searchQuery = "+someString"
        val expected = SearchFieldStringFunctionExpression(field, searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function extension on field with options`() {
        val field = someStringField(bucket = someBucket())
        val searchQuery = "+someString"
        val options = mapOf("index" to "someIndex")
        val expected = SearchFieldStringFunctionExpression(field, searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function expression on bucket`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, \"field:\"someString\"\")",
        )
        val underTest = SearchBucketStringFunctionExpression(
            someBucket(),
            searchQuery = "field:\"someString\"",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function expression on bucket with options`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, \"field:\"someString\"\", {\"index\" : \"someIndex\"})",
        )
        val underTest = SearchBucketStringFunctionExpression(
            someBucket(),
            searchQuery = "field:\"someString\"",
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function extension on bucket`() {
        val bucket = someBucket()
        val searchQuery = "field:\"someString\""
        val expected = SearchBucketStringFunctionExpression(bucket, searchQuery)

        val actual = fullTextSearch(bucket, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function extension on bucket with options`() {
        val bucket = someBucket()
        val searchQuery = "field:\"someString\""
        val options = mapOf("index" to "someIndex")
        val expected = SearchBucketStringFunctionExpression(bucket, searchQuery, options)

        val actual = fullTextSearch(bucket, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function expression`() {
        val field = someStringField(bucket = someBucket())
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, {\"field\" : \"someField\", \"analyzer\" : \"standard\"})",
        )
        val underTest = SearchFieldObjectFunctionExpression(field, mapOf("field" to "someField", "analyzer" to "standard"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function expression with options`() {
        val field = someStringField(bucket = someBucket())
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, " +
                "{\"field\" : \"someField\", \"analyzer\" : \"standard\"}, " +
                "{\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFieldObjectFunctionExpression(
            field,
            mapOf("field" to "someField", "analyzer" to "standard"),
            mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function extension`() {
        val field = someStringField(bucket = someBucket())
        val searchQuery = mapOf("field" to "someField", "analyzer" to "standard")
        val expected = SearchFieldObjectFunctionExpression(field, searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function extension with options`() {
        val field = someStringField(bucket = someBucket())
        val searchQuery = mapOf("field" to "someField", "analyzer" to "standard")
        val options = mapOf("index" to "someIndex")
        val expected = SearchFieldObjectFunctionExpression(field, searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function expression on bucket`() {
        val bucket = someBucket()
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, {\"field\" : \"someField\", \"analyzer\" : \"standard\"})",
        )
        val underTest = SearchBucketObjectFunctionExpression(bucket, mapOf("field" to "someField", "analyzer" to "standard"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function expression on bucket with options`() {
        val bucket = someBucket()
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, " +
                "{\"field\" : \"someField\", \"analyzer\" : \"standard\"}, " +
                "{\"index\" : \"someIndex\"})",
        )
        val underTest = SearchBucketObjectFunctionExpression(
            bucket,
            mapOf("field" to "someField", "analyzer" to "standard"),
            mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function extension on bucket`() {
        val bucket = someBucket()
        val searchQuery = mapOf("field" to "someField", "analyzer" to "standard")
        val expected = SearchBucketObjectFunctionExpression(bucket, searchQuery)

        val actual = fullTextSearch(bucket, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function extension on bucket with options`() {
        val bucket = someBucket()
        val searchQuery = mapOf("field" to "someField", "analyzer" to "standard")
        val options = mapOf("index" to "someIndex")
        val expected = SearchBucketObjectFunctionExpression(bucket, searchQuery, options)

        val actual = fullTextSearch(bucket, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search meta function expression`() {
        val expected = DopeQuery(
            queryString = "SEARCH_META()",
        )
        val underTest = SearchMetaFunctionExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search meta function expression with specified out name`() {
        val outName = "outName"
        val expected = DopeQuery(
            queryString = "SEARCH_META(`$outName`)",
        )
        val underTest = SearchMetaFunctionExpression(outName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search meta function extension`() {
        val expected = SearchMetaFunctionExpression()

        val actual = fullTextSearchMeta()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search meta function extension with specified out name`() {
        val outName = "outName"
        val expected = SearchMetaFunctionExpression(outName)

        val actual = fullTextSearchMeta(outName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search score function expression`() {
        val expected = DopeQuery(
            queryString = "SEARCH_SCORE()",
        )
        val underTest = SearchScoreFunctionExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search score function expression with specified out name`() {
        val outName = "outName"
        val expected = DopeQuery(
            queryString = "SEARCH_SCORE(`$outName`)",
        )
        val underTest = SearchScoreFunctionExpression(outName)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search score function extension`() {
        val expected = SearchScoreFunctionExpression()

        val actual = fullTextSearchScore()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search score function extension with specified out name`() {
        val outName = "outName"
        val expected = SearchScoreFunctionExpression(outName)

        val actual = fullTextSearchScore(outName)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
