package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someWindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FirstValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.RESPECT
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.firstValue
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstValueTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support first value with reference`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) OVER `ref`",
        )
        val underTest = FirstValue(someNumberField(), "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with reference and nulls modifier`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = FirstValue(someNumberField(), "ref", RESPECT)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with order clause`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = FirstValue(someNumberField(), windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with order clause and nulls modifier`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) IGNORE NULLS OVER (ORDER BY `stringField`)",
        )
        val underTest = FirstValue(someNumberField(), nullsModifier = IGNORE, windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with all window partition`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) OVER (PARTITION BY `booleanField` ORDER BY `stringField`)",
        )
        val underTest = FirstValue(
            someNumberField(),
            windowPartitionClause = listOf(someBooleanField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with frame clause`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) OVER (ORDER BY `stringField` RANGE UNBOUNDED PRECEDING)",
        )
        val underTest = FirstValue(
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
            windowFrameClause = someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value with all arguments`() {
        val expected = DopeQuery(
            "FIRST_VALUE(`numberField`) IGNORE NULLS OVER (PARTITION BY `booleanField` ORDER BY `stringField` RANGE UNBOUNDED PRECEDING)",
        )
        val underTest = FirstValue(
            someNumberField(),
            nullsModifier = IGNORE,
            windowPartitionClause = listOf(someBooleanField()),
            windowOrderClause = listOf(someOrderingTerm()),
            windowFrameClause = someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support first value function with reference`() {
        val expression = someNumberField()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = FirstValue(expression, windowReference, nullsModifier)

        val actual = firstValue(expression, windowReference, nullsModifier)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support first value function`() {
        val nullsModifier = IGNORE
        val windowPartitionClause = listOf(someBooleanField())
        val windowOrderClause = listOf(someOrderingTerm())
        val windowFrameClause = someWindowFrameClause()
        val expected = FirstValue(someNumberField(), nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        val actual = firstValue(someNumberField(), nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
