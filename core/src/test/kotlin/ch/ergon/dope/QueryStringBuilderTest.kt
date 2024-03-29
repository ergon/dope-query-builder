package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
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

        val actual = formatToQueryString(left, symbol, right)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format symbol with arguments`() {
        val symbol = "testsymbol"
        val arguments = arrayOf(1.toNumberType(), "hallo".toStringType())
        val expected = "$symbol ${arguments.joinToString(", ") { it.toQueryString() }}"

        val actual = formatToQueryString(symbol, *arguments)

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
        val arguments = arrayOf(1.toNumberType(), "hallo".toStringType())
        val expected = "$symbol(${arguments.joinToString(", ") { it.toQueryString() }})"

        val actual = formatToQueryStringWithBrackets(symbol, *arguments)

        assertEquals(expected, actual)
    }

    @Test
    fun `should format name with empty path`() {
        val name = "testName"
        val path = ""

        val actual = formatPathToQueryString(name, path)

        assertEquals(name, actual)
    }

    @Test
    fun `should format name with path`() {
        val name = "testName"
        val path = "testPath"
        val expected = "$path.$name"

        val actual = formatPathToQueryString(name, path)

        assertEquals(expected, actual)
    }
}
