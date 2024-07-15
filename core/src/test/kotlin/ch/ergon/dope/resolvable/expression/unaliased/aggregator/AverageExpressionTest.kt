package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageExpressionTest : ParameterDependentTest {
    @Test
    fun `should support average`() {
        val expected = DopeQuery(
            "AVG(`numberField`)",
            emptyMap(),
        )
        val underTest = AverageExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier ALL`() {
        val expected = DopeQuery(
            "AVG(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = AverageExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "AVG(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = AverageExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = AverageExpression(field, quantifier)

        val actual = avg(field, quantifier)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
