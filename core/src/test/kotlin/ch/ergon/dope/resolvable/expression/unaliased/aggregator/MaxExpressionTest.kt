package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxExpressionTest : ParameterDependentTest {
    @Test
    fun `should support max`() {
        val expected = DopeQuery(
            "MAX(`numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier ALL`() {
        val expected = DopeQuery(
            "MAX(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support max with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MAX(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = MaxExpression(someNumberField(), DISTINCT)

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
