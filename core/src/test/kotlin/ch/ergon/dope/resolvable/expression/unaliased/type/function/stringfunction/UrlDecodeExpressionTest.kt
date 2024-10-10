package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlDecodeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support url decode expression`() {
        val expected = DopeQuery(
            "URL_DECODE(`stringField`)",
        )
        val underTest = UrlDecodeExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support url decode extension type`() {
        val encodedString = someStringField()
        val expected = UrlDecodeExpression(encodedString)

        val actual = urlDecode(encodedString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support url decode extension string`() {
        val encodedString = someString()
        val expected = UrlDecodeExpression(encodedString.toDopeType())

        val actual = urlDecode(encodedString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
