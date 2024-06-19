package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class InExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support in`() {
        val expected = DopeQuery(
            "`stringField` IN `stringArrayField`",
            emptyMap(),
        )

        val actual = InExpression(someStringField(), someStringArrayField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "$1 IN `stringArrayField`",
            mapOf("$1" to parameterValue),
        )

        val actual = InExpression(parameterValue.asParameter(), someStringArrayField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = listOf("test")
        val expected = DopeQuery(
            "$1 IN $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = InExpression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
