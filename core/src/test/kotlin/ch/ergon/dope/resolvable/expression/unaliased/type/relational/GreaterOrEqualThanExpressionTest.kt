package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterOrEqualThanExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support greater or equals`() {
        val expected = DopeQuery(
            "`numberField` >= `numberField`",
            emptyMap(),
        )
        val underTest = GreaterOrEqualThanExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 >= `numberField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater or equals with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 >= $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = GreaterOrEqualThanExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
