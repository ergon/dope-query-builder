package ch.ergon.dope.resolvable.expression.windowfunction

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
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.windowfunction.NullsOrder.NULLS_LAST
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ordering term`() {
        val expected = DopeQuery(
            "`stringField`",
            emptyMap(),
        )
        val underTest = OrderingTerm(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with order type`() {
        val expected = DopeQuery(
            "`stringField` ASC",
            emptyMap(),
        )
        val underTest = OrderingTerm(someStringField(), OrderType.ASC)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with nulls order`() {
        val expected = DopeQuery(
            "`stringField` NULLS LAST",
            emptyMap(),
        )
        val underTest = OrderingTerm(someStringField(), nullsOrder = NULLS_LAST)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ordering term with order type and nulls order`() {
        val expected = DopeQuery(
            "`stringField` ASC NULLS LAST",
            emptyMap(),
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
            mapOf("$1" to value),
        )
        val underTest = OrderingTerm(value.asParameter(), OrderType.ASC, NULLS_LAST)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with one`() {
        val expected = DopeQuery(
            "`stringField`",
            emptyMap(),
        )
        val underTest = WindowFunctionArguments(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with two`() {
        val expected = DopeQuery(
            "`stringField`, `numberField`",
            emptyMap(),
        )
        val underTest = WindowFunctionArguments(someStringField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFunctionArgument with three`() {
        val expected = DopeQuery(
            "`stringField`, `numberField`, `booleanField`",
            emptyMap(),
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
            mapOf("$1" to value),
        )
        val underTest = WindowFunctionArguments(someStringField(), value.asParameter(), someBooleanField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support OverClauseWindowDefinition`() {
        val expected = DopeQuery(
            "OVER ()",
            emptyMap(),
        )
        val underTest = OverClauseWindowDefinition(someWindowDefinition())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support OverClauseWindowReference`() {
        val expected = DopeQuery(
            "OVER `someString`",
            emptyMap(),
        )
        val underTest = OverClauseWindowReference(someString())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition`() {
        val expected = DopeQuery(
            "",
            emptyMap(),
        )
        val underTest = WindowDefinition()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with window reference`() {
        val expected = DopeQuery(
            "`stringField`",
            emptyMap(),
        )
        val underTest = WindowDefinition(windowReference = someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with window partition`() {
        val expected = DopeQuery(
            "PARTITION BY `numberField`, `stringField`",
            emptyMap(),
        )
        val underTest = WindowDefinition(windowPartitionClause = listOf(someNumberField(), someStringField()))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with ordering`() {
        val expected = DopeQuery(
            "ORDER BY `stringField` NULLS LAST, `stringField` DESC",
            emptyMap(),
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
            emptyMap(),
        )
        val underTest = WindowDefinition(windowFrameClause = someWindowFrameClause())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowDefinition with all arguments`() {
        val expected = DopeQuery(
            "`stringField` PARTITION BY `numberField`, `booleanField` ORDER BY `stringField` RANGE UNBOUNDED PRECEDING",
            emptyMap(),
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
            emptyMap(),
        )
        val underTest = WindowFrameClause(WindowFrameType.GROUPS, someWindowFrameExtent())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support WindowFrameClause and exclusion`() {
        val expected = DopeQuery(
            "GROUPS UNBOUNDED PRECEDING EXCLUDE GROUP",
            emptyMap(),
        )
        val underTest = WindowFrameClause(WindowFrameType.GROUPS, someWindowFrameExtent(), someWindowFrameExclusion())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support UnboundedPreceding`() {
        val expected = DopeQuery(
            "UNBOUNDED PRECEDING",
            emptyMap(),
        )
        val underTest = UnboundedPreceding()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Preceding`() {
        val expected = DopeQuery(
            "`numberField` PRECEDING",
            emptyMap(),
        )
        val underTest = Preceding(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CurrentRow`() {
        val expected = DopeQuery(
            "CURRENT ROW",
            emptyMap(),
        )
        val underTest = CurrentRow()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between and`() {
        val expected = DopeQuery(
            "BETWEEN CURRENT ROW AND CURRENT ROW",
            emptyMap(),
        )
        val underTest = Between(CurrentRow(), CurrentRow())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support unbounded following`() {
        val expected = DopeQuery(
            "UNBOUNDED FOLLOWING",
            emptyMap(),
        )
        val underTest = UnboundedFollowing()

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support following`() {
        val expected = DopeQuery(
            "`numberField` FOLLOWING",
            emptyMap(),
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
