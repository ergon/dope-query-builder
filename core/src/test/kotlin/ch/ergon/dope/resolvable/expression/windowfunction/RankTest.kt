package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Rank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.rank
import kotlin.test.Test
import kotlin.test.assertEquals

class RankTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support rank with reference`() {
        val expected = DopeQuery(
            "RANK() OVER `ref`",
        )
        val underTest = Rank("ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank with order clause`() {
        val expected = DopeQuery(
            "RANK() OVER (ORDER BY `stringField`)",
        )
        val underTest = Rank(windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank with partition and order clause`() {
        val expected = DopeQuery(
            "RANK() OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = Rank(
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support rank function with reference`() {
        val windowReference = "ref"
        val expected = Rank(windowReference)

        val actual = rank(windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support rank function with partition and order clause`() {
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = Rank(windowPartitionClause, windowOrderClause)

        val actual = rank(windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
