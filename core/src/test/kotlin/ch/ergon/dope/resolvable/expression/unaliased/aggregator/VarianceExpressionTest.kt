package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class VarianceExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support variance`() {
        val expected = DopeQuery(
            "VARIANCE(`numberField`)",
            emptyMap(),
        )
        val underTest = VarianceExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier ALL`() {
        val expected = DopeQuery(
            "VARIANCE(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = VarianceExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "VARIANCE(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = VarianceExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support variance function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = VarianceExpression(field, quantifier)

        val actual = variance(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
