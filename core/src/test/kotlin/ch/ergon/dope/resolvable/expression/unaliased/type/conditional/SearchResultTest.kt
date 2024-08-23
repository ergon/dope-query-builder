package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchResultTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support search result expression`() {
        val expected = DopeQuery(
            "`stringField`, `numberField`",
            emptyMap(),
        )
        val underTest = SearchResult(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search result expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "$1, `numberField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = SearchResult(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search result expression with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`stringField`, $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = SearchResult(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search result expression with all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "$1, $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = SearchResult(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search result extension function with unaliased unaliased`() {
        val searchExpression = someStringField()
        val resultExpression = someNumberField()
        val expected = SearchResult(searchExpression, resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with unaliased number`() {
        val searchExpression = someStringField()
        val resultExpression = someNumber()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with unaliased string`() {
        val searchExpression = someStringField()
        val resultExpression = someString()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with unaliased boolean`() {
        val searchExpression = someStringField()
        val resultExpression = someBoolean()
        val expected = SearchResult(searchExpression, resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with number unaliased`() {
        val searchExpression = someNumber()
        val resultExpression = someStringField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with string unaliased`() {
        val searchExpression = someString()
        val resultExpression = someStringField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with boolean unaliased`() {
        val searchExpression = someBoolean()
        val resultExpression = someStringField()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression)

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with number number`() {
        val searchExpression = someNumber()
        val resultExpression = someNumber()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with number string`() {
        val searchExpression = someNumber()
        val resultExpression = someString()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with number boolean`() {
        val searchExpression = someNumber()
        val resultExpression = someBoolean()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with string number`() {
        val searchExpression = someString()
        val resultExpression = someNumber()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with string string`() {
        val searchExpression = someString()
        val resultExpression = someString()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with string boolean`() {
        val searchExpression = someString()
        val resultExpression = someBoolean()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with boolean number`() {
        val searchExpression = someBoolean()
        val resultExpression = someNumber()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with boolean string`() {
        val searchExpression = someBoolean()
        val resultExpression = someString()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support search result extension function with boolean boolean`() {
        val searchExpression = someBoolean()
        val resultExpression = someBoolean()
        val expected = SearchResult(searchExpression.toDopeType(), resultExpression.toDopeType())

        val actual = searchExpression.resultsIn(resultExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
