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

class NowUtcExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOW_UTC without format`() {
        val expected = DopeQuery(
            queryString = "NOW_UTC()",
        )
        val underTest = NowUtcExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_UTC with positional parameter format`() {
        val fmt = "yyyy-MM-dd"
        val expected = DopeQuery(
            queryString = "NOW_UTC($1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = NowUtcExpression(fmt.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_UTC with named parameter format`() {
        val fmt = "yyyy-MM-dd"
        val name = "f"
        val expected = DopeQuery(
            queryString = "NOW_UTC(\$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = NowUtcExpression(fmt.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support utcNowString extension`() {
        val expected = NowUtcExpression()
        val actual = utcNowString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support utcNowString extension with field`() {
        val fmtField = someStringField()
        val expected = NowUtcExpression(fmtField)
        val actual = utcNowString(fmtField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support utcNowString extension with raw string`() {
        val raw = someString()
        val expected = NowUtcExpression(raw.toDopeType())
        val actual = utcNowString(raw)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
