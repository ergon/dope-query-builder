package ch.ergon.dope.extensions.expression.type.function.search

import ch.ergon.dope.extension.expression.type.function.search.fullTextSearch
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionFieldObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionFieldStringExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchFunctionTest {
    @Test
    fun `should support full text search function with string search query`() {
        val field = someCMStringField()
        val searchQuery = "+something"
        val expected = SearchFunctionFieldStringExpression(field.toDopeType(), searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support full text search function with string search query and options`() {
        val field = someCMStringField()
        val searchQuery = "+something"
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionFieldStringExpression(field.toDopeType(), searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support full text search function with object search query`() {
        val field = someCMStringField()
        val searchQuery = mapOf("match" to "someString")
        val expected = SearchFunctionFieldObjectExpression(field.toDopeType(), searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support full text search function with object search query and options`() {
        val field = someCMStringField()
        val searchQuery = mapOf("match" to "someString")
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionFieldObjectExpression(field.toDopeType(), searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected, actual)
    }
}
