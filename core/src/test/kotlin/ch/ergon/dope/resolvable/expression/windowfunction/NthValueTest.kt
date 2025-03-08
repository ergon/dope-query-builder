package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someWindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier.FIRST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier.LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NthValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.RESPECT
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.nthValue
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NthValueTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support nth value with reference`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) OVER `ref`",
        )
        val underTest = NthValue(someNumberField(), someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with reference and nulls modifier`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = NthValue(someNumberField(), someNumberField(), nullsModifier = RESPECT, windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with order clause`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = NthValue(someNumberField(), someNumberField(), windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with order clause and nulls modifier`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) IGNORE NULLS OVER (ORDER BY `stringField`)",
        )
        val underTest = NthValue(
            someNumberField(),
            someNumberField(),
            nullsModifier = IGNORE,
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with from modifier`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) FROM FIRST OVER (ORDER BY `stringField`)",
        )
        val underTest = NthValue(
            someNumberField(),
            someNumberField(),
            fromModifier = FIRST,
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with all window partition`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = NthValue(
            someNumberField(),
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value with frame clause`() {
        val expected = DopeQuery(
            "NTH_VALUE (`numberField`, `numberField`) OVER (ORDER BY `stringField` RANGE UNBOUNDED PRECEDING)",
        )
        val underTest = NthValue(
            someNumberField(),
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
            windowFrameClause = someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nth value function with reference`() {
        val expression = someNumberField()
        val offset = someNumberField()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = NthValue(expression, offset, nullsModifier, windowReference = windowReference)

        val actual = nthValue(expression, offset, nullsModifier, windowReference = windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nth value function with reference and number`() {
        val expression = someNumberField()
        val offset = someNumber()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = NthValue(expression, offset.toDopeType(), nullsModifier, windowReference = windowReference)

        val actual = nthValue(expression, offset, nullsModifier, windowReference = windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support nth value function with all parameters`() {
        val expression = someNumberField()
        val offset = someNumberField()
        val nullsModifier = IGNORE
        val fromModifier = LAST
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val windowFrameClause = someWindowFrameClause()
        val expected = NthValue(expression, offset, nullsModifier, fromModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        val actual = nthValue(expression, offset, nullsModifier, fromModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
