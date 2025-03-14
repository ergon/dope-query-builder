package ch.ergon.dope.extensions.expression.type.function.search

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.search.fullTextSearch
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support full text search function with string search query`() {
        val field = someCMStringField()
        val searchQuery = "+something"
        val expected = SearchFunctionExpression(field.toDopeType(), searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support full text search function with string search query and options`() {
        val field = someCMStringField()
        val searchQuery = "+something"
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(field.toDopeType(), searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support full text search function with object search query`() {
        val field = someCMStringField()
        val searchQuery = mapOf("match" to "someString")
        val expected = SearchFunctionExpression(field.toDopeType(), searchQuery)

        val actual = fullTextSearch(field, searchQuery)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support full text search function with object search query and options`() {
        val field = someCMStringField()
        val searchQuery = mapOf("match" to "someString")
        val options = mapOf("index" to "someIndex")
        val expected = SearchFunctionExpression(field.toDopeType(), searchQuery, options)

        val actual = fullTextSearch(field, searchQuery, options)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
