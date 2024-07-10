package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support max`() {
        val expected = DopeQuery(
            "MAX(`numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(CMNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier ALL`() {
        val expected = DopeQuery(
            "MAX(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(CMNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MAX(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(CMNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max function`() {
        val field = someStringField()
        val quantifier = DISTINCT
        val expected = MaxExpression(field, quantifier)

        val actual = max(field, quantifier)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
