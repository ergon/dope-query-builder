package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.CountExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.count
import kotlin.test.Test
import kotlin.test.assertEquals

class CountExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support count`() {
        val expected = DopeQuery(
            queryString = "COUNT(`numberField`)",
        )
        val underTest = CountExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "COUNT(ALL `numberField`)",
        )
        val countExpression = CountExpression(someNumberField(), ALL)

        val actual = countExpression.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "COUNT(DISTINCT `numberField`)",
        )
        val countExpression = CountExpression(someNumberField(), DISTINCT)

        val actual = countExpression.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count function`() {
        val field = someStringField()
        val quantifier = DISTINCT
        val expected = CountExpression(field, quantifier)

        val actual = count(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
