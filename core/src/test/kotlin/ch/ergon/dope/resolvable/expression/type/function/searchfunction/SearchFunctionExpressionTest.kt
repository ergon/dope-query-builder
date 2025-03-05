package ch.ergon.dope.resolvable.expression.type.function.searchfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionExpression
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
        val underTest = SearchFunctionExpression(
            field = field,
            stringSearchExpression = "someString",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function expression on field with options`() {
        val field = someStringField(bucket = someBucket())
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, \"+someString\", {\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFunctionExpression(
            field = field,
            stringSearchExpression = "+someString",
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function extension on field`() {
        val field = someStringField(bucket = someBucket())
        val searchExpression = "+someString"
        val expected = SearchFunctionExpression(field, searchExpression)

        val actual = fullTextSearch(field, searchExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function extension on field with options`() {
        val field = someStringField(bucket = someBucket())
        val searchExpression = "+someString"
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(field, searchExpression, options)

        val actual = fullTextSearch(field, searchExpression, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function expression on bucket`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, \"field:\"someString\"\")",
        )
        val underTest = SearchFunctionExpression(
            bucket = someBucket(),
            stringSearchExpression = "field:\"someString\"",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function expression on bucket with options`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, \"field:\"someString\"\", {\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFunctionExpression(
            bucket = someBucket(),
            stringSearchExpression = "field:\"someString\"",
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search string function extension on bucket`() {
        val bucket = someBucket()
        val searchExpression = "field:\"someString\""
        val expected = SearchFunctionExpression(bucket, searchExpression)

        val actual = fullTextSearch(bucket, searchExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search string function extension on bucket with options`() {
        val bucket = someBucket()
        val searchExpression = "field:\"someString\""
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(bucket, searchExpression, options)

        val actual = fullTextSearch(bucket, searchExpression, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function expression`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, {\"field\" : \"someField\", \"analyzer\" : \"standard\"})",
        )
        val underTest = SearchFunctionExpression(
            field = someStringField(bucket = someBucket()),
            objectSearchExpression = mapOf("field" to "someField", "analyzer" to "standard"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function expression with options`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`.`stringField`, " +
                "{\"field\" : \"someField\", \"analyzer\" : \"standard\"}, " +
                "{\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFunctionExpression(
            field = someStringField(bucket = someBucket()),
            objectSearchExpression = mapOf("field" to "someField", "analyzer" to "standard"),
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function extension`() {
        val field = someStringField(bucket = someBucket())
        val searchExpression = mapOf("field" to "someField", "analyzer" to "standard")
        val expected = SearchFunctionExpression(field, searchExpression)

        val actual = fullTextSearch(field, searchExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function extension with options`() {
        val field = someStringField(bucket = someBucket())
        val searchExpression = mapOf("field" to "someField", "analyzer" to "standard")
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(field, searchExpression, options)

        val actual = fullTextSearch(field, searchExpression, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function expression on bucket`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, {\"field\" : \"someField\", \"analyzer\" : \"standard\"})",
        )
        val underTest = SearchFunctionExpression(
            bucket = someBucket(),
            objectSearchExpression = mapOf("field" to "someField", "analyzer" to "standard"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function expression on bucket with options`() {
        val expected = DopeQuery(
            queryString = "SEARCH(`someBucket`, " +
                "{\"field\" : \"someField\", \"analyzer\" : \"standard\"}, " +
                "{\"index\" : \"someIndex\"})",
        )
        val underTest = SearchFunctionExpression(
            bucket = someBucket(),
            objectSearchExpression = mapOf("field" to "someField", "analyzer" to "standard"),
            options = mapOf("index" to "someIndex"),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search object function extension on bucket`() {
        val bucket = someBucket()
        val searchExpression = mapOf("field" to "someField", "analyzer" to "standard")
        val expected = SearchFunctionExpression(bucket, searchExpression)

        val actual = fullTextSearch(bucket, searchExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search object function extension on bucket with options`() {
        val bucket = someBucket()
        val searchExpression = mapOf("field" to "someField", "analyzer" to "standard")
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(bucket, searchExpression, options)

        val actual = fullTextSearch(bucket, searchExpression, options)

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
