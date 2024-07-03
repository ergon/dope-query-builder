package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NotLikeExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support not like`() {
        val expected = DopeQuery(
            "`stringField` NOT LIKE `stringField`",
            emptyMap(),
        )
        val underTest = NotLikeExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "`stringField` NOT LIKE $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotLikeExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
