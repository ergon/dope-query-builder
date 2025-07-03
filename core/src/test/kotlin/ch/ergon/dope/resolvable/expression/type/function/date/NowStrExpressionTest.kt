package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NowStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOW_STR without format`() {
        val expected = DopeQuery(
            queryString = "NOW_STR()",
        )
        val underTest = NowStringExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_STR with positional parameter format`() {
        val fmt = "yyyy"
        val expected = DopeQuery(
            queryString = "NOW_STR($1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = NowStringExpression(fmt.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_STR with named parameter format`() {
        val fmt = "yyyy"
        val name = "f"
        val expected = DopeQuery(
            queryString = "NOW_STR(\$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = NowStringExpression(fmt.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nowString extension with field`() {
        val fmtField = someStringField()
        val expected = NowStringExpression(fmtField)
        val actual = nowString(fmtField)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nowString extension with raw`() {
        val fmt = "yyy-mm-dd"
        val expected = NowStringExpression(fmt.toDopeType())
        val actual = nowString(fmt)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
