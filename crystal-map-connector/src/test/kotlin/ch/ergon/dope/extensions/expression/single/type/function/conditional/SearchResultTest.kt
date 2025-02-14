package ch.ergon.dope.extensions.expression.single.type.function.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.function.conditional.resultsIn
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMObjectField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.SearchResult
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchResultTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support resultsIn with CMNumber and type`() {
        val searchExpression = someCMNumberField()
        val resultExpression = someNumberField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with CMString and type`() {
        val searchExpression = someCMStringField()
        val resultExpression = someNumberField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with CMBoolean`() {
        val searchExpression = someCMBooleanField()
        val resultExpression = someNumberField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with CMObject`() {
        val searchExpression = someCMObjectField()
        val resultExpression = someNumberField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with type and CMNumber`() {
        val searchExpression = someStringField()
        val resultExpression = someCMNumberField()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with type and CMString`() {
        val searchExpression = someStringField()
        val resultExpression = someCMStringField()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with type and CMBoolean`() {
        val searchExpression = someNumberField()
        val resultExpression = someCMBooleanField()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }

    @Test
    fun `should support resultsIn with type and CMObject`() {
        val searchExpression = someNumberField()
        val resultExpression = someCMObjectField()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.searchExpression.toDopeQuery(manager), actual.searchExpression.toDopeQuery(manager))
        assertEquals(expected.resultExpression.toDopeQuery(manager), actual.resultExpression.toDopeQuery(manager))
    }
}
