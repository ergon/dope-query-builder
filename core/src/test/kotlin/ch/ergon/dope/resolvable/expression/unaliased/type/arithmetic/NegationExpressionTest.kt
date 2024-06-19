package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support negation`() {
        val expected = DopeQuery(
            "-`numberField`",
            emptyMap(),
        )

        val actual = NegationExpression(someNumberField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "-$1",
            mapOf("$1" to parameterValue),
        )

        val actual = NegationExpression(parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
