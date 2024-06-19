package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NotExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support not`() {
        val expected = DopeQuery(
            "NOT `booleanField`",
            emptyMap(),
        )

        val actual = NotExpression(someBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "NOT $1",
            mapOf("$1" to parameterValue),
        )

        val actual = NotExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
