package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support like`() {
        val expected = DopeQuery(
            "`stringField` LIKE `stringField`",
            emptyMap(),
        )

        val actual = LikeExpression(someStringField(), someStringField()).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "`stringField` LIKE $1",
            mapOf("$1" to parameterValue),
        )

        val actual = LikeExpression(someStringField(), parameterValue.asParameter()).toDopeQuery()

        assertEquals(expected, actual)
    }
}
