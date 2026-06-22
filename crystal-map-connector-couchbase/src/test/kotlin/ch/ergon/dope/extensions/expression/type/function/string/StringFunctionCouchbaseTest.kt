package ch.ergon.dope.extensions.expression.type.function.string

import ch.ergon.dope.extension.expression.type.function.string.reverse
import ch.ergon.dope.extension.expression.type.function.string.urlDecode
import ch.ergon.dope.extension.expression.type.function.string.urlEncode
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.type.function.string.ReverseExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UrlDecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UrlEncodeExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionCouchbaseTest {
    @Test
    fun `should support Reverse with CM string`() {
        val string = someCMStringField()
        val expected = ReverseExpression(string.toDopeType())

        val actual = string.reverse()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support UrlDecode with CM string`() {
        val string = someCMStringField()
        val expected = UrlDecodeExpression(string.toDopeType())

        val actual = string.urlDecode()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support UrlEncode with CM string`() {
        val string = someCMStringField()
        val expected = UrlEncodeExpression(string.toDopeType())

        val actual = string.urlEncode()

        assertEquals(expected, actual)
    }
}
