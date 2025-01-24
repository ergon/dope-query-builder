package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class DenseRankTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support dense rank with reference`() {
        val expected = DopeQuery(
            "DENSE_RANK () OVER `ref`",
        )
        val underTest = DenseRank("ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank with ordering term`() {
        val expected = DopeQuery(
            "DENSE_RANK () OVER (ORDER BY `stringField`)",
        )
        val underTest = DenseRank(listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank with ordering term and partition`() {
        val expected = DopeQuery(
            "DENSE_RANK () OVER (PARTITION BY `stringField`, `numberField` ORDER BY `stringField`)",
        )
        val underTest = DenseRank(listOf(someOrderingTerm()), listOf(someStringField(), someNumberField()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dense rank function with reference`() {
        val windowReference = "ref"
        val expected = DenseRank(windowReference)

        val actual = denseRank(windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support dense rank function`() {
        val windowOrderClause = listOf(someOrderingTerm())
        val windowPartitionClause = listOf(someStringField(), someNumberField())
        val expected = DenseRank(windowOrderClause, windowPartitionClause)

        val actual = denseRank(windowOrderClause, windowPartitionClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
