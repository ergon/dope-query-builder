package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MeanExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support mean`() {
        val expected = DopeQuery(
            "MEAN(`numberField`)",
            emptyMap(),
        )
        val underTest = MeanExpression(CMNumberField(), null)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier ALL`() {
        val expected = DopeQuery(
            "MEAN(ALL `numberField`)",
            emptyMap(),
        )
        val underTest = MeanExpression(CMNumberField(), ALL)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean with quantifier DISTINCT`() {
        val expected = DopeQuery(
            "MEAN(DISTINCT `numberField`)",
            emptyMap(),
        )
        val underTest = MeanExpression(CMNumberField(), DISTINCT)

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support mean function`() {
        val field = CMNumberField()
        val quantifier = DISTINCT
        val expected = MeanExpression(field, quantifier)

        val actual = mean(field, quantifier)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
