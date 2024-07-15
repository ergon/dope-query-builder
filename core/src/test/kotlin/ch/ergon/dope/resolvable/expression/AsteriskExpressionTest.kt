package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBucket
import kotlin.test.Test
import kotlin.test.assertEquals

class AsteriskExpressionTest : ParameterDependentTest {
    @Test
    fun `should support asterisk`() {
        val expected = DopeQuery(
            "*",
            emptyMap(),
        )
        val underTest = AsteriskExpression()

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support asterisk with bucket`() {
        val expected = DopeQuery(
            "`someBucket`.*",
            emptyMap(),
        )
        val underTest = AsteriskExpression(someBucket())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
