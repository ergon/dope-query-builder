package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class AsteriskExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

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
