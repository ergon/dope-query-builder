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
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClauseWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClauseWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameExclusion
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameType
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFunctionArguments
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionTest : ManagerDependentTest {
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
    fun `should support WindowFunctionArgument with one`() {
        val expected = DopeQuery(
            "`stringField`",
        )
        val underTest = WindowFunctionArguments(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with two`() {
        val expected = DopeQuery(
            "`stringField`, `numberField`",
        )
        val underTest = WindowFunctionArguments(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with three`() {
        val expected = DopeQuery(
            "`stringField`, `numberField`, `booleanField`",
        )
        val underTest = WindowFunctionArguments(someStringField(), someNumberField(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with three and parameter`() {
        val value = someNumber()
        val expected = DopeQuery(
            "`stringField`, $1, `booleanField`",
            DopeParameters(positionalParameters = listOf(value)),
        )
        val underTest = WindowFunctionArguments(someStringField(), value.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support OverClauseWindowDefinition`() {
        val expected = DopeQuery(
            "OVER ()",
        )
        val underTest = OverClauseWindowDefinition(someWindowDefinition())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support OverClauseWindowReference`() {
        val expected = DopeQuery(
            "OVER `someString`",
        )
        val underTest = OverClauseWindowReference(someString())

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
    fun `should support Preceding`() {
        val expected = DopeQuery(
            "`numberField` PRECEDING",
        )
        val underTest = Preceding(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Preceding with number`() {
        val expected = DopeQuery(
            "5 PRECEDING",
        )
        val underTest = Preceding(someNumber())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
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
    fun `should support between and`() {
        val expected = DopeQuery(
            "BETWEEN CURRENT ROW AND CURRENT ROW",
        )
        val underTest = Between(CurrentRow(), CurrentRow())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
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
    fun `should support following`() {
        val expected = DopeQuery(
            "`numberField` FOLLOWING",
        )
        val underTest = Following(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
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
