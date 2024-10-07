package ch.ergon.dope.extensions.type.stringfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.stringfunction.length
import ch.ergon.dope.extension.type.stringfunction.lower
import ch.ergon.dope.extension.type.stringfunction.mbLength
import ch.ergon.dope.extension.type.stringfunction.nowString
import ch.ergon.dope.extension.type.stringfunction.reverse
import ch.ergon.dope.extension.type.stringfunction.suffixes
import ch.ergon.dope.extension.type.stringfunction.upper
import ch.ergon.dope.extension.type.stringfunction.urlDecode
import ch.ergon.dope.extension.type.stringfunction.urlEncode
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.LengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.LowerExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.MBLengthExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.NowStringExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.ReverseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.SuffixesExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.UpperExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.UrlDecodeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.UrlEncodeExpression
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support Length with CM string`() {
        val string = someCMStringField()
        val expected = LengthExpression(string.toDopeType())

        val actual = length(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Lower with CM string`() {
        val string = someCMStringField()
        val expected = LowerExpression(string.toDopeType())

        val actual = lower(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MBLength with CM string`() {
        val string = someCMStringField()
        val expected = MBLengthExpression(string.toDopeType())

        val actual = mbLength(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowString with CM string`() {
        val string = someCMStringField()
        val expected = NowStringExpression(string.toDopeType())

        val actual = nowString(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Reverse with CM string`() {
        val string = someCMStringField()
        val expected = ReverseExpression(string.toDopeType())

        val actual = reverse(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Suffixes with CM string`() {
        val string = someCMStringField()
        val expected = SuffixesExpression(string.toDopeType())

        val actual = suffixes(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Upper with CM string`() {
        val string = someCMStringField()
        val expected = UpperExpression(string.toDopeType())

        val actual = upper(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support UrlDecode with CM string`() {
        val string = someCMStringField()
        val expected = UrlDecodeExpression(string.toDopeType())

        val actual = urlDecode(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support UrlEncode with CM string`() {
        val string = someCMStringField()
        val expected = UrlEncodeExpression(string.toDopeType())

        val actual = urlEncode(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}