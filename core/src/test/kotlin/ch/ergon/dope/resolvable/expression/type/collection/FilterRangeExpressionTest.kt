package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someAnyTypeArrayField
import ch.ergon.dope.helper.someAnyTypeField
import ch.ergon.dope.helper.someAnyTypeSelectRawClause
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.IN
import ch.ergon.dope.resolvable.expression.type.collection.MembershipType.WITHIN
import ch.ergon.dope.resolvable.expression.type.function.type.isNumber
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support filter for in expression extension with condition type`() {
        val range = someNumberArrayField()
        val expected = FilterRangeExpression(
            membershipType = IN,
            range = range,
            iteratorName = "it",
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = range.filter(iteratorName = "it") {
            it.isLessOrEqualThan(2)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for in expression extension with condition collection`() {
        val range = listOf(someNumberField(), someNumberField())
        val expected = FilterRangeExpression(
            membershipType = IN,
            range = range.toDopeType(),
            iteratorName = "it",
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = range.filter(iteratorName = "it") {
            it.isLessOrEqualThan(2)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for in expression extension with condition select clause`() {
        val range = someNumberSelectRawClause()
        val expected = FilterRangeExpression(
            membershipType = IN,
            range = range.asExpression(),
            iteratorName = "it",
            condition = { it.isLessOrEqualThan(2) },
        )

        val actual = range.filter(iteratorName = "it") {
            it.isLessOrEqualThan(2)
        }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition type`() {
        val range = someAnyTypeArrayField()
        val expected = FilterRangeExpression(
            membershipType = WITHIN,
            range = range,
            iteratorName = "it",
            condition = { it.isNumber() },
        )

        val actual = range.filterUnnested(iteratorName = "it") { it.isNumber() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition collection`() {
        val range = listOf(someAnyTypeField(), someAnyTypeField())
        val expected = FilterRangeExpression(
            membershipType = WITHIN,
            range = range.toDopeType(),
            iteratorName = "it",
            condition = { it.isNumber() },
        )

        val actual = range.filterUnnested(iteratorName = "it") { it.isNumber() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support filter for within expression extension with condition select clause`() {
        val range = someAnyTypeSelectRawClause()
        val expected = FilterRangeExpression(
            membershipType = WITHIN,
            range = range.asExpression(),
            iteratorName = "it",
            condition = { it.isNumber() },
        )

        val actual = range.filterUnnested(iteratorName = "it") { it.isNumber() }

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
