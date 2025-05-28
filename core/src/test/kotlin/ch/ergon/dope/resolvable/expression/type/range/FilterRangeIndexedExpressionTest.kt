package ch.ergon.dope.resolvable.expression.type.range

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someAnyTypeField
import ch.ergon.dope.helper.someAnyTypeSelectRawClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.range.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterRangeIndexedExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support filter for in expression extension with condition type`() {
        val range = someNumberArrayField()
        val expected = FilterRangeIndexedExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexed(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for in expression extension with condition collection`() {
        val range = listOf(someNumberField(), someNumberField())
        val expected = FilterRangeIndexedExpression(
            membershipType = IN,
            range = range.toDopeType(),
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexed(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for in expression extension with condition select clause`() {
        val range = someNumberSelectRawClause()
        val expected = FilterRangeIndexedExpression(
            membershipType = IN,
            range = range.asExpression(),
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexed(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition type`() {
        val range = someAnyTypeArrayField()
        val expected = FilterRangeIndexedExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexedUnnested(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition collection`() {
        val range = listOf(someAnyTypeField(), someAnyTypeField())
        val expected = FilterRangeIndexedExpression(
            membershipType = WITHIN,
            range = range.toDopeType(),
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexedUnnested(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition select clause`() {
        val range = someAnyTypeSelectRawClause()
        val expected = FilterRangeIndexedExpression(
            membershipType = WITHIN,
            range = range.asExpression(),
            iteratorName = "it",
            indexName = "i",
            condition = { i, _ -> i.isLessOrEqualThan(2) },
        )

        val actual =
            range.filterIndexedUnnested(iteratorName = "it", indexName = "i") { i, _ ->
                i.isLessOrEqualThan(2)
            }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
