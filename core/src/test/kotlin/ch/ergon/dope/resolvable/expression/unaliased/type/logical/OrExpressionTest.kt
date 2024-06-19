package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class OrExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support or`() {
        val expected = DopeQuery(
            "(`booleanField` OR `booleanField`)",
            emptyMap(),
        )

        val actual = OrExpression(someBooleanField(), someBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with parameter`() {
        val parameterValue = true
        val expected = DopeQuery(
            "($1 OR `booleanField`)",
            mapOf("$1" to parameterValue),
        )

        val actual = OrExpression(parameterValue.asParameter(), someBooleanField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support or with all parameter`() {
        val parameterValue = true
        val parameterValue2 = true
        val expected = DopeQuery(
            "($1 OR $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )

        val actual = OrExpression(parameterValue.asParameter(), parameterValue2.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
