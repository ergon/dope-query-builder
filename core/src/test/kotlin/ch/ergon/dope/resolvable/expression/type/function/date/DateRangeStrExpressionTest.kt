package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.CENTURY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DAY
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.DECADE
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.HOUR
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MILLISECOND
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MINUTE
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.MONTH
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.QUARTER
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.SECOND
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.WEEK
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType.YEAR
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
            QUARTER,
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
            MONTH,
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
            YEAR,
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
            DAY,
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
            HOUR,
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
            MINUTE,
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
            SECOND,
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
            MONTH,
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
            YEAR,
            inc.asParameter(names[2]),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with TypeExpression endDate`() {
        val expr = someStringField().dateRangeBy(someStringField(), QUARTER)
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            QUARTER,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate`() {
        val expr = someStringField().dateRangeBy("2022-01-01", CENTURY)
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-01-01".toDopeType(),
            CENTURY,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with field increment`() {
        val expr = someStringField().dateRangeBy(
            someStringField(),
            MONTH,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            MONTH,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw increment`() {
        val expr = someStringField().dateRangeBy(
            someStringField(),
            DAY,
            7,
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            someStringField(),
            DAY,
            7.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and field increment`() {
        val expr = someStringField().dateRangeBy(
            "2022-02-02",
            DECADE,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-02-02".toDopeType(),
            DECADE,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and raw increment`() {
        val expr = someStringField().dateRangeBy(
            "2022-03-03",
            WEEK,
            2,
        )
        val expected = DateRangeStrExpression(
            someStringField(),
            "2022-03-03".toDopeType(),
            WEEK,
            2.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with TypeExpression endDate`() {
        val raw = "2022-04-04"
        val expr = raw.dateRangeBy(someStringField(), HOUR)
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            HOUR,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate`() {
        val raw = "2022-05-05"
        val expr = raw.dateRangeBy("2022-06-06", MINUTE)
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-06-06".toDopeType(),
            MINUTE,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with field increment`() {
        val raw = "2022-07-07"
        val expr = raw.dateRangeBy(
            someStringField(),
            SECOND,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            SECOND,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw increment`() {
        val raw = "2022-08-08"
        val expr = raw.dateRangeBy(
            someStringField(),
            MILLISECOND,
            9,
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            someStringField(),
            MILLISECOND,
            9.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate and field increment`() {
        val raw = "2022-09-09"
        val expr = raw.dateRangeBy(
            "2022-10-10",
            CENTURY,
            someNumberField(),
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-10-10".toDopeType(),
            CENTURY,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String dateRangeBy extension with raw endDate and raw increment`() {
        val raw = "2022-11-11"
        val expr = raw.dateRangeBy(
            "2022-12-12",
            DECADE,
            3,
        )
        val expected = DateRangeStrExpression(
            raw.toDopeType(),
            "2022-12-12".toDopeType(),
            DECADE,
            3.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
