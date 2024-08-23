package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class MeanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support mean`() {
        val expected = DopeQuery(
            "MEAN(`numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MeanExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier ALL`() {
        val expected = DopeQuery(
            "MEAN(ALL `numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MeanExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MEAN(DISTINCT `numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MeanExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = MeanExpression(field, quantifier)

        val actual = mean(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
