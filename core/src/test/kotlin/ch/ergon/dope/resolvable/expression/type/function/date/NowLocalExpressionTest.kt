package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowLocalExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOW_LOCAL without format`() {
        val expected = DopeQuery(
            queryString = "NOW_LOCAL()",
        )
        val underTest = NowLocalExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_LOCAL with positional parameter format`() {
        val fmt = "yyyy-MM-dd"
        val expected = DopeQuery(
            queryString = "NOW_LOCAL($1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = NowLocalExpression(fmt.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_LOCAL with named parameter format`() {
        val fmt = "yyyy-MM-dd"
        val name = "f"
        val expected = DopeQuery(
            queryString = "NOW_LOCAL(\$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = NowLocalExpression(fmt.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support localNowString extension with field`() {
        val fmtField = someStringField()
        val expected = NowLocalExpression(fmtField)
        val actual = localNowString(fmtField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support localNowString extension with raw string`() {
        val raw = someString()
        val expected = NowLocalExpression(raw.toDopeType())
        val actual = localNowString(raw)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
