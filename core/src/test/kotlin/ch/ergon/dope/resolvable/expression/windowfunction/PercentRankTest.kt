package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class PercentRankTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support percent rank with reference`() {
        val expected = DopeQuery(
            "PERCENT_RANK () OVER `ref`",
            emptyMap(),
        )
        val underTest = PercentRank("ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support percent rank with order clause`() {
        val expected = DopeQuery(
            "PERCENT_RANK () OVER (ORDER BY `stringField`)",
            emptyMap(),
        )
        val underTest = PercentRank(windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support percent rank with partition and order clause`() {
        val expected = DopeQuery(
            "PERCENT_RANK () OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
            emptyMap(),
        )
        val underTest = PercentRank(
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support percent rank function with reference`() {
        val windowReference = "ref"
        val expected = PercentRank(windowReference)

        val actual = percentRank(windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support percent rank function with partition and order clause`() {
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = PercentRank(windowPartitionClause, windowOrderClause)

        val actual = percentRank(windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
