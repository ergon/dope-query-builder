package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateRangeStrExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_RANGE_STR with fields and no increment`() {
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(`stringField`, `stringField`, \"QUARTER\")",
        )
        val underTest = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Quarter,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with positional startDate parameter`() {
        val startVal = "2021-01-01"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR($1, `stringField`, \"MONTH\")",
            DopeParameters(positionalParameters = listOf(startVal)),
        )
        val underTest = DateRangeStrExpression(
            startVal.asParameter(),
            someStringField(),
            Month,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with named startDate parameter`() {
        val startVal = "2021-02-01"
        val name = "s"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(\$$name, `stringField`, \"YEAR\")",
            DopeParameters(namedParameters = mapOf(name to startVal)),
        )
        val underTest = DateRangeStrExpression(
            startVal.asParameter(name),
            someStringField(),
            Year,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with positional endDate parameter`() {
        val endVal = "2021-03-01"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(`stringField`, $1, \"DAY\")",
            DopeParameters(positionalParameters = listOf(endVal)),
        )
        val underTest = DateRangeStrExpression(
            someStringField(),
            endVal.asParameter(),
            Day,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with named endDate parameter`() {
        val endVal = "2021-04-01"
        val name = "e"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(`stringField`, \$$name, \"HOUR\")",
            DopeParameters(namedParameters = mapOf(name to endVal)),
        )
        val underTest = DateRangeStrExpression(
            someStringField(),
            endVal.asParameter(name),
            Hour,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with positional increment parameter`() {
        val inc = 2
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(`stringField`, `stringField`, \"MINUTE\", $1)",
            DopeParameters(positionalParameters = listOf(inc)),
        )
        val underTest = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Minute,
            inc.asParameter(),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with named increment parameter`() {
        val inc = 3
        val name = "n"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(`stringField`, `stringField`, \"SECOND\", \$$name)",
            DopeParameters(namedParameters = mapOf(name to inc)),
        )
        val underTest = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Second,
            inc.asParameter(name),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with all positional parameters`() {
        val s = "start"
        val e = "end"
        val inc = 4
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR($1, $2, \"MONTH\", $3)",
            DopeParameters(positionalParameters = listOf(s, e, inc)),
        )
        val underTest = DateRangeStrExpression(
            s.asParameter(),
            e.asParameter(),
            Month,
            inc.asParameter(),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_STR with all named parameters`() {
        val s = "sVal"
        val e = "eVal"
        val inc = 5
        val names = listOf("s", "e", "i")
        val expected = DopeQuery(
            queryString = "DATE_RANGE_STR(\$${names[0]}, \$${names[1]}, \"YEAR\", \$${names[2]})",
            DopeParameters(
                namedParameters = mapOf(
                    names[0] to s,
                    names[1] to e,
                    names[2] to inc,
                ),
            ),
        )
        val underTest = DateRangeStrExpression(
            s.asParameter(names[0]),
            e.asParameter(names[1]),
            Year,
            inc.asParameter(names[2]),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with TypeExpression endDate`() {
        val expr = someStringField().dateRangeBy(someStringField(), Quarter)
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Quarter,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate`() {
        val expr = someStringField().dateRangeBy("2022-01-01", Century)
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-01-01".toDopeType(),
            Century,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with field increment`() {
        val expr = someStringField().dateRangeBy(
            someStringField(),
            Month,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Month,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw increment`() {
        val expr = someStringField().dateRangeBy(
            someStringField(),
            Day,
            7,
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            Day,
            7.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and field increment`() {
        val expr = someStringField().dateRangeBy(
            "2022-02-02",
            Decade,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-02-02".toDopeType(),
            Decade,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and raw increment`() {
        val expr = someStringField().dateRangeBy(
            "2022-03-03",
            Week,
            2,
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-03-03".toDopeType(),
            Week,
            2.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with TypeExpression endDate`() {
        val raw = "2022-04-04"
        val expr = raw.dateRangeBy(someStringField(), Hour)
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            Hour,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate`() {
        val raw = "2022-05-05"
        val expr = raw.dateRangeBy("2022-06-06", Minute)
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-06-06".toDopeType(),
            Minute,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with field increment`() {
        val raw = "2022-07-07"
        val expr = raw.dateRangeBy(
            someStringField(),
            Second,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            Second,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw increment`() {
        val raw = "2022-08-08"
        val expr = raw.dateRangeBy(
            someStringField(),
            Millisecond,
            9,
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            Millisecond,
            9.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate and field increment`() {
        val raw = "2022-09-09"
        val expr = raw.dateRangeBy(
            "2022-10-10",
            Century,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-10-10".toDopeType(),
            Century,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate and raw increment`() {
        val raw = "2022-11-11"
        val expr = raw.dateRangeBy(
            "2022-12-12",
            Decade,
            3,
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-12-12".toDopeType(),
            Decade,
            3.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
