package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.stdDev
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardDeviationExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support standard deviation`() {
        val expected = DopeQuery(
            queryString = "STDDEV(`numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "STDDEV(ALL `numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "STDDEV(DISTINCT `numberField`)",
        )
        val underTest = StandardDeviationExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support standard deviation function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = StandardDeviationExpression(field, quantifier)

        val actual = stdDev(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
