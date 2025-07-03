package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NowMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support NOW_MILLIS`() {
        val expected = DopeQuery(
            queryString = "NOW_MILLIS()",
        )
        val underTest = NowMillisExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nowEpochMillis extension`() {
        val expected = NowMillisExpression()
        val actual = nowEpochMillis()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
