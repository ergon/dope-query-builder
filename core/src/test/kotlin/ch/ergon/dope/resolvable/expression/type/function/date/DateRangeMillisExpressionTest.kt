package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateRangeMillisExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support DATE_RANGE_MILLIS with fields and no increment`() {
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(`numberField`, `numberField`, \"MONTH\")",
        )
        val underTest = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Month,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with positional startDate parameter`() {
        val startVal = 1000L
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS($1, `numberField`, \"WEEK\")",
            DopeParameters(positionalParameters = listOf(startVal)),
        )
        val underTest = DateRangeMillisExpression(
            startVal.asParameter(),
            someNumberField(),
            Week,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with named startDate parameter`() {
        val startVal = 2000L
        val name = "s"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(\$$name, `numberField`, \"YEAR\")",
            DopeParameters(namedParameters = mapOf(name to startVal)),
        )
        val underTest = DateRangeMillisExpression(
            startVal.asParameter(name),
            someNumberField(),
            Year,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with positional endDate parameter`() {
        val endVal = 3000L
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(`numberField`, $1, \"DAY\")",
            DopeParameters(positionalParameters = listOf(endVal)),
        )
        val underTest = DateRangeMillisExpression(
            someNumberField(),
            endVal.asParameter(),
            Day,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with named endDate parameter`() {
        val endVal = 4000L
        val name = "e"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(`numberField`, \$$name, \"HOUR\")",
            DopeParameters(namedParameters = mapOf(name to endVal)),
        )
        val underTest = DateRangeMillisExpression(
            someNumberField(),
            endVal.asParameter(name),
            Hour,
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with positional increment parameter`() {
        val inc = 2L
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(`numberField`, `numberField`, \"MINUTE\", $1)",
            DopeParameters(positionalParameters = listOf(inc)),
        )
        val underTest = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Minute,
            inc.asParameter(),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with named increment parameter`() {
        val inc = 3L
        val name = "n"
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(`numberField`, `numberField`, \"SECOND\", \$$name)",
            DopeParameters(namedParameters = mapOf(name to inc)),
        )
        val underTest = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Second,
            inc.asParameter(name),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with all positional parameters`() {
        val s = 10L
        val e = 20L
        val inc = 4L
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS($1, $2, \"MONTH\", $3)",
            DopeParameters(positionalParameters = listOf(s, e, inc)),
        )
        val underTest = DateRangeMillisExpression(
            s.asParameter(),
            e.asParameter(),
            Month,
            inc.asParameter(),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support DATE_RANGE_MILLIS with all named parameters`() {
        val s = 11L
        val e = 22L
        val inc = 5L
        val names = listOf("s", "e", "i")
        val expected = DopeQuery(
            queryString = "DATE_RANGE_MILLIS(\$${names[0]}, \$${names[1]}, \"YEAR\", \$${names[2]})",
            DopeParameters(
                namedParameters = mapOf(
                    names[0] to s,
                    names[1] to e,
                    names[2] to inc,
                ),
            ),
        )
        val underTest = DateRangeMillisExpression(
            s.asParameter(names[0]),
            e.asParameter(names[1]),
            Year,
            inc.asParameter(names[2]),
        )
        assertEquals(expected, underTest.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with TypeExpression endDate`() {
        val expr = someNumberField().dateRangeBy(someNumberField(), Quarter)
        val expected = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Quarter,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate`() {
        val expr = someNumberField().dateRangeBy(1234L, Century)
        val expected = DateRangeMillisExpression(
            someNumberField(),
            1234L.toDopeType(),
            Century,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with field increment expression`() {
        val expr = someNumberField().dateRangeBy(
            someNumberField(),
            Month,
            someNumberField(),
        )
        val expected = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Month,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw increment expression`() {
        val expr = someNumberField().dateRangeBy(
            someNumberField(),
            Day,
            7L.toDopeType(),
        )
        val expected = DateRangeMillisExpression(
            someNumberField(),
            someNumberField(),
            Day,
            7L.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and field increment`() {
        val expr = someNumberField().dateRangeBy(
            4321L,
            Decade,
            someNumberField(),
        )
        val expected = DateRangeMillisExpression(
            someNumberField(),
            4321L.toDopeType(),
            Decade,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support dateRangeBy extension with raw endDate and raw increment`() {
        val expr = someNumberField().dateRangeBy(
            5555L,
            Week,
            2L,
        )
        val expected = DateRangeMillisExpression(
            someNumberField(),
            5555L.toDopeType(),
            Week,
            2L.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with TypeExpression endDate`() {
        val expr = 999L.dateRangeBy(someNumberField(), Hour)
        val expected = DateRangeMillisExpression(
            999L.toDopeType(),
            someNumberField(),
            Hour,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with raw endDate`() {
        val expr = 100L.dateRangeBy(200L, Minute)
        val expected = DateRangeMillisExpression(
            100L.toDopeType(),
            200L.toDopeType(),
            Minute,
            null,
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with field increment`() {
        val expr = 111L.dateRangeBy(someNumberField(), Second, someNumberField())
        val expected = DateRangeMillisExpression(
            111L.toDopeType(),
            someNumberField(),
            Second,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with raw increment`() {
        val expr = 222L.dateRangeBy(someNumberField(), Millisecond, 9L)
        val expected = DateRangeMillisExpression(
            222L.toDopeType(),
            someNumberField(),
            Millisecond,
            9L.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with raw endDate and field increment`() {
        val expr = 333L.dateRangeBy(444L, Century, someNumberField())
        val expected = DateRangeMillisExpression(
            333L.toDopeType(),
            444L.toDopeType(),
            Century,
            someNumberField(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support type dateRangeBy extension with type endDate and raw increment`() {
        val expr = someNumberField().dateRangeBy(someNumberField("other"), Century, 444L)
        val expected = DateRangeMillisExpression(
            someNumberField(),
            someNumberField("other"),
            Century,
            444L.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Number dateRangeBy extension with raw endDate and raw increment`() {
        val expr = 777L.dateRangeBy(888L, Decade, 3L)
        val expected = DateRangeMillisExpression(
            777L.toDopeType(),
            888L.toDopeType(),
            Decade,
            3L.toDopeType(),
        )
        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
