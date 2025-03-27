package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Lag
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.RESPECT
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lag
import kotlin.test.Test
import kotlin.test.assertEquals

class LagTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support lag with reference`() {
        val expected = DopeQuery(
            "LAG(`numberField`) OVER `ref`",
        )
        val underTest = Lag(someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with reference and nulls modifier`() {
        val expected = DopeQuery(
            "LAG(`numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = Lag(someNumberField(), nullsModifier = RESPECT, windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with offset`() {
        val expected = DopeQuery(
            "LAG(`numberField`, `numberField`) OVER `ref`",
        )
        val underTest = Lag(someNumberField(), offset = someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with default`() {
        val expected = DopeQuery(
            "LAG(`numberField`, `numberField`) OVER `ref`",
        )
        val underTest = Lag(someNumberField(), default = someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with all`() {
        val expected = DopeQuery(
            "LAG(`numberField`, `numberField`, `numberField`) IGNORE NULLS OVER `ref`",
        )
        val underTest = Lag(
            someNumberField(),
            nullsModifier = IGNORE,
            offset = someNumberField(),
            default = someNumberField(),
            windowReference = "ref",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with order clause`() {
        val expected = DopeQuery(
            "LAG(`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = Lag(someNumberField(), windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with order clause and nulls modifier`() {
        val expected = DopeQuery(
            "LAG(`numberField`) IGNORE NULLS OVER (ORDER BY `stringField`)",
        )
        val underTest = Lag(
            someNumberField(),
            nullsModifier = IGNORE,
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with order clause and offset`() {
        val expected = DopeQuery(
            "LAG(`numberField`, `numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = Lag(
            someNumberField(),
            offset = someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag with all window partition`() {
        val expected = DopeQuery(
            "LAG(`numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = Lag(
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lag function with reference`() {
        val expression = someNumberField()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = Lag(expression, nullsModifier = nullsModifier, windowReference = windowReference)

        val actual = lag(expression, nullsModifier = nullsModifier, windowReference = windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support lag function with ordering term`() {
        val expression = someNumberField()
        val nullsModifier = RESPECT
        val expected = Lag(expression, nullsModifier = nullsModifier, windowOrderClause = listOf(someOrderingTerm()))

        val actual = lag(expression, nullsModifier = nullsModifier, windowOrderClause = listOf(someOrderingTerm()))

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
