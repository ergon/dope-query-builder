package ch.ergon.dope.resolvable.expression.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.aggregate.median
import kotlin.test.Test
import kotlin.test.assertEquals

class MedianExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support median`() {
        val expected = DopeQuery(
            queryString = "MEDIAN(`numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier ALL`() {
        val expected = DopeQuery(
            queryString = "MEDIAN(ALL `numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier DISTINCT`() {
        val expected = DopeQuery(
            queryString = "MEDIAN(DISTINCT `numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = MedianExpression(field, quantifier)

        val actual = median(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
