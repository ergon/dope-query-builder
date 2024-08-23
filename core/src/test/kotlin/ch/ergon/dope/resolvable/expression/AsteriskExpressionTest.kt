package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class AsteriskExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support asterisk`() {
        val expected = DopeQuery(
            "*",
            emptyMap(),
            manager,
        )
        val underTest = AsteriskExpression()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk with bucket`() {
        val expected = DopeQuery(
            "`someBucket`.*",
            emptyMap(),
            manager,
        )
        val underTest = AsteriskExpression(someBucket())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }
}
