package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlEncodeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support url encode expression`() {
        val expected = DopeQuery(
            "URL_ENCODE(`stringField`)",
        )
        val underTest = UrlEncodeExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support url encode extension type`() {
        val string = someStringField()
        val expected = UrlEncodeExpression(string)

        val actual = urlEncode(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support url encode extension string`() {
        val string = someString()
        val expected = UrlEncodeExpression(string.toDopeType())

        val actual = urlEncode(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
