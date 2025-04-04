package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.avg
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support average`() {
        val expected = DopeQuery(
            queryString = "AVG(`numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "AVG(ALL `numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "AVG(DISTINCT `numberField`)",
        )
        val underTest = AverageExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support average function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = AverageExpression(field, quantifier)

        val actual = avg(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
