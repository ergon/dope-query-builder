package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class CuntExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support count`() {
        val expected = DopeQuery(
            "COUNT(`numberField`)",
            emptyMap(),
        )

        val actual = CountExpression(someNumberField(), null).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier ALL`() {
        val expected = DopeQuery(
            "COUNT(ALL `numberField`)",
            emptyMap(),
        )

        val actual = CountExpression(someNumberField(), ALL).toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support count with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "COUNT(DISTINCT `numberField`)",
            emptyMap(),
        )

        val actual = CountExpression(someNumberField(), DISTINCT).toDopeQuery()

        assertEquals(expected, actual)
    }
}
