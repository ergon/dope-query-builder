package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import kotlin.test.Test
import kotlin.test.assertEquals

class MinExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support min`() {
        val expected = DopeQuery(
            "MIN(`numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MinExpression(someNumberField(), null)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with quantifier ALL`() {
        val expected = DopeQuery(
            "MIN(ALL `numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MinExpression(someNumberField(), ALL)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MIN(DISTINCT `numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = MinExpression(someNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support min function`() {
        val field = someNumberField()
        val quantifier = DISTINCT
        val expected = MinExpression(field, quantifier)

        val actual = min(field, quantifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
