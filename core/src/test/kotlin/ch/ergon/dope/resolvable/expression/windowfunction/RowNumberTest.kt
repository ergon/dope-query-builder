package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import kotlin.test.Test
import kotlin.test.assertEquals

class RowNumberTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support row number with reference`() {
        val expected = DopeQuery(
            "ROW_NUMBER () OVER `ref`",
            emptyMap(),
        )
        val underTest = RowNumber("ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support row number with order clause`() {
        val expected = DopeQuery(
            "ROW_NUMBER () OVER (ORDER BY `stringField`)",
            emptyMap(),
        )
        val underTest = RowNumber(windowOrderClause = listOf(someOrderingTerm()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support row number with partition and order clause`() {
        val expected = DopeQuery(
            "ROW_NUMBER () OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
            emptyMap(),
        )
        val underTest = RowNumber(
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support row number function with reference`() {
        val windowReference = "ref"
        val expected = RowNumber(windowReference)

        val actual = rowNumber(windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support row number function with partition and order clause`() {
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = RowNumber(windowPartitionClause, windowOrderClause)

        val actual = rowNumber(windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
