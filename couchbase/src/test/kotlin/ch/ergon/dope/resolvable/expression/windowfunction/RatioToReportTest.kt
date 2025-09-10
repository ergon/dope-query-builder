package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someOrderingTerm
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.someWindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.RESPECT
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.RatioToReport
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.RatioToReportWithReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.ratioToReport
import kotlin.test.Test
import kotlin.test.assertEquals

class RatioToReportTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager<CouchbaseDopeQuery>

    @Test
    fun `should support ratio to report with reference`() {
        val expected = CouchbaseDopeQuery(
            "RATIO_TO_REPORT(`numberField`) OVER `ref`",
        )
        val underTest = RatioToReportWithReference(someNumberField(), windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ratio to report with reference and nulls modifier`() {
        val expected = CouchbaseDopeQuery(
            "RATIO_TO_REPORT(`numberField`) RESPECT NULLS OVER `ref`",
        )
        val underTest = RatioToReportWithReference(someNumberField(), nullsModifier = RESPECT, windowReference = "ref")

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ratio to report with order clause`() {
        val expected = CouchbaseDopeQuery(
            "RATIO_TO_REPORT(`numberField`) OVER (ORDER BY `stringField`)",
        )
        val underTest = RatioToReport(
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ratio to report with partition and order clause`() {
        val expected = CouchbaseDopeQuery(
            "RATIO_TO_REPORT(`numberField`) OVER (PARTITION BY `stringField` ORDER BY `stringField`)",
        )
        val underTest = RatioToReport(
            someNumberField(),
            windowPartitionClause = listOf(someStringField()),
            windowOrderClause = listOf(someOrderingTerm()),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ratio to report with frame clause`() {
        val expected = CouchbaseDopeQuery(
            "RATIO_TO_REPORT(`numberField`) OVER (ORDER BY `stringField` RANGE UNBOUNDED PRECEDING)",
        )
        val underTest = RatioToReport(
            someNumberField(),
            windowOrderClause = listOf(someOrderingTerm()),
            windowFrameClause = someWindowFrameClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ratio to report function with reference`() {
        val expression = someNumberField()
        val windowReference = "ref"
        val expected = RatioToReportWithReference(expression, windowReference = windowReference)

        val actual = ratioToReport(expression, windowReference = windowReference)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ratio to report function with partition and order clause`() {
        val expression = someNumberField()
        val windowPartitionClause = listOf(someStringField())
        val windowOrderClause = listOf(someOrderingTerm())
        val expected = RatioToReport(expression, windowPartitionClause, windowOrderClause)

        val actual = ratioToReport(expression, windowPartitionClause, windowOrderClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
