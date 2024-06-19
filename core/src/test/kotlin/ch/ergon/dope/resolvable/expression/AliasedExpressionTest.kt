package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AliasedExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support aliased expression`() {
        val expected = DopeQuery(
            "`stringField` AS `test`",
            emptyMap(),
        )

        val actual = AliasedExpression(someStringField(), "test").toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support aliased expression with parameter`() {
        val parameterValue = "testValue"
        val expected = DopeQuery(
            "$1 AS `test`",
            mapOf("$1" to parameterValue),
        )

        val actual = AliasedExpression(parameterValue.asParameter(), "test").toDopeQuery()

        assertEquals(expected, actual)
    }
}
