package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NTile
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.ntile
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NTileTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ntile with reference`() {
        val expected = DopeQuery(
            "NTILE (`numberField`) OVER `ref`",
        )
        val underTest = NTile(someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ntile with order clause`() {
        val expected = DopeQuery(
            "NTILE (`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = NTile(
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ntile with partition and order clause`() {
        val expected = DopeQuery(
            "NTILE (`numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = NTile(
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ntile function with reference`() {
        val numTiles = someNumberField()
        val windowReference = "ref"
        val expected = NTile(numTiles, windowReference)

        val actual = ntile(numTiles, windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ntile function with reference and number`() {
        val numTiles = someNumber()
        val windowReference = "ref"
        val expected = NTile(numTiles.toDopeType(), windowReference)

        val actual = ntile(numTiles, windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ntile function with partition and order clause`() {
        val numTiles = someNumberField()
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = NTile(numTiles, windowPartitionClause, windowOrderClause)

        val actual = ntile(numTiles, windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ntile function with partition, order clause and number`() {
        val numTiles = someNumber()
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = NTile(numTiles.toDopeType(), windowPartitionClause, windowOrderClause)

        val actual = ntile(numTiles, windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
