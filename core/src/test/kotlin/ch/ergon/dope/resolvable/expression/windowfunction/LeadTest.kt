package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.windowfunction.NullsModifier.RESPECT
import kotlin.test.Test
import kotlin.test.assertEquals

class LeadTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support lead with reference`() {
        val expected = DopeQuery(
            "LEAD (`numberField`) OVER `ref`",
        )
        val underTest = Lead(someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with reference and nulls modifier`() {
        val expected = DopeQuery(
            "LEAD (`numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = Lead(someNumberField(), nullsModifier = RESPECT, windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with offset`() {
        val expected = DopeQuery(
            "LEAD (`numberField`, `numberField`) OVER `ref`",
        )
        val underTest = Lead(someNumberField(), offset = someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with default`() {
        val expected = DopeQuery(
            "LEAD (`numberField`, `numberField`) OVER `ref`",
        )
        val underTest = Lead(someNumberField(), default = someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with all parameters`() {
        val expected = DopeQuery(
            "LEAD (`numberField`, `numberField`, `numberField`) IGNORE NULLS OVER `ref`",
        )
        val underTest = Lead(
            someNumberField(),
            offset = someNumberField(),
            default = someNumberField(),
            nullsModifier = IGNORE,
            windowReference = "ref",
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with order clause`() {
        val expected = DopeQuery(
            "LEAD (`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = Lead(someNumberField(), windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with order clause and nulls modifier`() {
        val expected = DopeQuery(
            "LEAD (`numberField`) IGNORE NULLS OVER (ORDER BY `stringField`)",
        )
        val underTest = Lead(
            someNumberField(),
            nullsModifier = IGNORE,
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with order clause and offset`() {
        val expected = DopeQuery(
            "LEAD (`numberField`, `numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = Lead(
            someNumberField(),
            offset = someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead with all window partition`() {
        val expected = DopeQuery(
            "LEAD (`numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = Lead(
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support lead function with reference`() {
        val expression = someNumberField()
        val windowReference = "ref"
        val nullsModifier = RESPECT
        val expected = Lead(expression, nullsModifier = nullsModifier, windowReference = windowReference)

        val actual = lead(expression, nullsModifier = nullsModifier, windowReference = windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support lead function with all parameters`() {
        val expression = someNumberField()
        val offset = someNumberField()
        val default = someStringField()
        val nullsModifier = IGNORE
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = Lead(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

        val actual = lead(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
