package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someWindowDefinition
import ch.ergon.dope.helper.someWindowFrameClause
import ch.ergon.dope.helper.someWindowFrameExclusion
import ch.ergon.dope.helper.someWindowFrameExtent
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameExclusion
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameType
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.currentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.orderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.unboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.unboundedPreceding
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ordering term`() {
        val expected = DopeQuery(
            "`stringField`",
        )
        val underTest = OrderingTerm(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with order type`() {
        val expected = DopeQuery(
            "`stringField` ASC",
        )
        val underTest = OrderingTerm(someStringField(), OrderType.ASC)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with nulls order`() {
        val expected = DopeQuery(
            "`stringField` NULLS LAST",
        )
        val underTest = OrderingTerm(someStringField(), nullsOrder = NULLS_LAST)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with order type and nulls order`() {
        val expected = DopeQuery(
            "`stringField` ASC NULLS LAST",
        )
        val underTest = OrderingTerm(someStringField(), OrderType.ASC, NULLS_LAST)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with parameter and order type and nulls order`() {
        val value = someString()
        val expected = DopeQuery(
            "$1 ASC NULLS LAST",
            DopeParameters(positionalParameters = listOf(value)),
        )
        val underTest = OrderingTerm(value.asParameter(), OrderType.ASC, NULLS_LAST)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term function`() {
        val value = someStringField()
        val expected = OrderingTerm(value)

        val actual = orderingTerm(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support OverClauseWindowDefinition`() {
        val expected = DopeQuery(
            "OVER ()",
        )
        val underTest = OverWindowDefinition(someWindowDefinition())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support OverClauseWindowReference`() {
        val expected = DopeQuery(
            "OVER `someString`",
        )
        val underTest = OverWindowReference(someString())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition`() {
        val expected = DopeQuery(
            "",
        )
        val underTest = WindowDefinition()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with window reference`() {
        val expected = DopeQuery(
            "`stringField`",
        )
        val underTest = WindowDefinition(windowReference = someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with window partition`() {
        val expected = DopeQuery(
            "PARTITION BY `numberField`, `stringField`",
        )
        val underTest = WindowDefinition(windowPartitionClause = listOf(someNumberField(), someStringField()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with ordering`() {
        val expected = DopeQuery(
            "ORDER BY `stringField` NULLS LAST, `stringField` DESC",
        )
        val underTest =
            WindowDefinition(windowOrderClause = listOf(someOrderingTerm(nullsOrder = NULLS_LAST), someOrderingTerm(orderType = DESC)))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with window frame`() {
        val expected = DopeQuery(
            "RANGE UNBOUNDED PRECEDING",
        )
        val underTest = WindowDefinition(windowFrameClause = someWindowFrameClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with all arguments`() {
        val expected = DopeQuery(
            "`stringField` PARTITION BY `numberField`, `booleanField` ORDER BY `stringField` RANGE UNBOUNDED PRECEDING",
        )
        val underTest = WindowDefinition(
            someStringField(),
            listOf(someNumberField(), someBooleanField()),
            listOf(someOrderingTerm()),
            someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameClause`() {
        val expected = DopeQuery(
            "GROUPS UNBOUNDED PRECEDING",
        )
        val underTest = WindowFrameClause(WindowFrameType.GROUPS, someWindowFrameExtent())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameClause and exclusion`() {
        val expected = DopeQuery(
            "GROUPS UNBOUNDED PRECEDING EXCLUDE GROUP",
        )
        val underTest = WindowFrameClause(WindowFrameType.GROUPS, someWindowFrameExtent(), someWindowFrameExclusion())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support UnboundedPreceding`() {
        val expected = DopeQuery(
            "UNBOUNDED PRECEDING",
        )
        val underTest = UnboundedPreceding()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support UnboundedPreceding function`() {
        val expected = UnboundedPreceding()

        val actual = unboundedPreceding()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Preceding`() {
        val expected = DopeQuery(
            "`numberField` PRECEDING",
        )
        val underTest = Preceding(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Preceding function`() {
        val value = someNumberField()
        val expected = Preceding(value)

        val actual = preceding(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Preceding function with number`() {
        val value = someNumber()
        val expected = Preceding(value.toDopeType())

        val actual = preceding(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support CurrentRow`() {
        val expected = DopeQuery(
            "CURRENT ROW",
        )
        val underTest = CurrentRow()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CurrentRow function`() {
        val expected = CurrentRow()

        val actual = currentRow()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support between and`() {
        val expected = DopeQuery(
            "BETWEEN CURRENT ROW AND CURRENT ROW",
        )
        val underTest = Between(CurrentRow(), CurrentRow())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Between function`() {
        val value = CurrentRow()
        val expected = Between(value, value)

        val actual = between(value, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support unbounded following`() {
        val expected = DopeQuery(
            "UNBOUNDED FOLLOWING",
        )
        val underTest = UnboundedFollowing()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unbounded following function`() {
        val expected = UnboundedFollowing()

        val actual = unboundedFollowing()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support following`() {
        val expected = DopeQuery(
            "`numberField` FOLLOWING",
        )
        val underTest = Following(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Following function`() {
        val value = someNumberField()
        val expected = Following(value)

        val actual = following(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Following function with number`() {
        val value = someNumber()
        val expected = Following(value.toDopeType())

        val actual = following(value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WindowFrameExclusion current row`() {
        val expected = "EXCLUDE CURRENT ROW"
        val underTest = WindowFrameExclusion.EXCLUDE_CURRENT_ROW

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameExclusion group`() {
        val expected = "EXCLUDE GROUP"
        val underTest = WindowFrameExclusion.EXCLUDE_GROUP

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameExclusion ties`() {
        val expected = "EXCLUDE TIES"
        val underTest = WindowFrameExclusion.EXCLUDE_TIES

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameExclusion no others`() {
        val expected = "EXCLUDE NO OTHERS"
        val underTest = WindowFrameExclusion.EXCLUDE_NO_OTHERS

        val actual = underTest.queryString

        assertEquals(expected, actual)
    }
}
