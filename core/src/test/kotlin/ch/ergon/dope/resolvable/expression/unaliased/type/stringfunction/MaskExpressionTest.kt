package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MaskExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support mask`() {
        val expected = DopeQuery(
            "MASK(`stringField`, {\"mask\": \"*\"})",
            emptyMap(),
        )
        val underTest = MaskExpression(someStringField(), mapOf("mask" to "*"))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mask with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "MASK($1, {\"mask\": \"*\"})",
            mapOf("$1" to parameterValue),
        )
        val underTest = MaskExpression(parameterValue.asParameter(), mapOf("mask" to "*"))

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
