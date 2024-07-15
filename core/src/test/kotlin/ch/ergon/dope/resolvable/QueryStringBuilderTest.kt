package ch.ergon.dope.resolvable

import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryStringBuilderTest {
    @Test
    fun `should format left and right`() {
        val left = "testLeft"
        val right = "testRight"
        val expected = "$left $right"

        val actual = formatToQueryString(left, right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format left and right with symbol`() {
        val left = "testLeft"
        val symbol = "testSymbol"
        val right = "testRight"
        val expected = "$left $symbol $right"

        val actual = formatToQueryStringWithSymbol(left, symbol, right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format symbol with arguments`() {
        val symbol = "testsymbol"
        val arguments = arrayOf(1.toDopeType(), "hallo".toDopeType())
        val expected = "$symbol ${arguments.joinToString(", ") { it.toDopeQuery().queryString }}"

        val actual = formatToQueryString(symbol, *arguments.map { it.toDopeQuery().queryString }.toTypedArray())

        assertEquals(expected, actual)
    }

    @Test
    fun `should format left, right and symbol with brackets`() {
        val left = "testLeft"
        val symbol = "testSymbol"
        val right = "testRight"
        val expected = "($left $symbol $right)"

        val actual = formatToQueryStringWithBrackets(left, symbol, right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format symbol with arguments and brackets`() {
        val symbol = "testsymbol"
        val arguments = arrayOf(1.toDopeType(), "hallo".toDopeType())
        val expected = "$symbol(${arguments.joinToString(", ") { it.toDopeQuery().queryString }})"

        val actual = formatToQueryStringWithBrackets(symbol, *arguments.map { it.toDopeQuery().queryString }.toTypedArray())

        assertEquals(expected, actual)
    }

    @Test
    fun `should format name with empty path`() {
        val name = "testName"
        val path = ""
        val expected = "`$name`"

        val actual = formatPathToQueryString(name, path)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format name with path`() {
        val name = "testName"
        val path = "testPath"
        val expected = "`$path`.`$name`"

        val actual = formatPathToQueryString(name, path)

        assertEquals(expected, actual)
    }
}
