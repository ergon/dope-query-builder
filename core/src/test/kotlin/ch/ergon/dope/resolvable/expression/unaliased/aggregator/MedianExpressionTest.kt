package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class MedianExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support median`() {
        val expected = DopeQuery(
            "MEDIAN(`numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier ALL`() {
        val expected = DopeQuery(
            "MEDIAN(ALL `numberField`)",
        )
        val underTest = MedianExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support median with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MEDIAN(DISTINCT `numberField`)",
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
