package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArrayAggregateExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support array aggregate`() {
        val expected = DopeQuery(
            "ARRAY_AGG(`stringField`)",
            emptyMap(),
        )
        val underTest = ArrayAggregateExpression(someStringField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier ALL`() {
        val expected = DopeQuery(
            "ARRAY_AGG(ALL `stringField`)",
            emptyMap(),
        )
        val underTest = ArrayAggregateExpression(someStringField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support array aggregate with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "ARRAY_AGG(DISTINCT `stringField`)",
            emptyMap(),
        )
        val underTest = ArrayAggregateExpression(someStringField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }
}
