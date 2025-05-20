package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ClockMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support CLOCK_MILLIS`() {
        val expected = DopeQuery(
            queryString = "CLOCK_MILLIS()",
        )
        val underTest = ClockMillisExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support clockMillis extension`() {
        val expected = ClockMillisExpression()
        val actual = clockMillis()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
