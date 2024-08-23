package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class CountExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support count`() {
        val expected = DopeQuery(
            "COUNT(`numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = CountExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier ALL`() {
        val expected = DopeQuery(
            "COUNT(ALL `numberField`)",
            emptyMap(),
            manager,
        )
        val countExpression = CountExpression(someNumberField(), ALL)

        val actual = countExpression.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "COUNT(DISTINCT `numberField`)",
            emptyMap(),
            manager,
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
