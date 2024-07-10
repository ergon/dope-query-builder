package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
        val underTest = NegationExpression(CMNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "-$1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NegationExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation function type`() {
        val type = CMNumberField()
        val expected = NegationExpression(type)

        val actual = neg(type)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support negation function number`() {
        val number = someNumber()
        val expected = NegationExpression(number.toDopeType())

        val actual = neg(number)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
