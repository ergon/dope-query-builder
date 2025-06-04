package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class StrToTimezoneExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support STR_TO_TZ with field timezone`() {
        val dateField = someStringField()
        val tzField = someStringField()
        val expected = DopeQuery(
            queryString = "STR_TO_TZ(`stringField`, `stringField`)",
        )
        val underTest = StrToTimezoneExpression(dateField, tzField)

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_TZ with positional tz parameter`() {
        val date = someStringField()
        val tz = "UTC"
        val expected = DopeQuery(
            queryString = "STR_TO_TZ(`stringField`, $1)",
            DopeParameters(positionalParameters = listOf(tz)),
        )
        val underTest = StrToTimezoneExpression(date, tz.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_TZ with named tz parameter`() {
        val date = someStringField()
        val tz = "UTC"
        val name = "zone"
        val expected = DopeQuery(
            queryString = "STR_TO_TZ(`stringField`, \$$name)",
            DopeParameters(namedParameters = mapOf(name to tz)),
        )
        val underTest = StrToTimezoneExpression(date, tz.asParameter(name))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toTimeZone extension on TypeExpression`() {
        val expr = someStringField().toTimeZone("Europe/London".toDopeType())
        val expected = StrToTimezoneExpression(someStringField(), "Europe/London".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support String toTimeZone extension`() {
        val raw = someString()
        val expr = raw.toTimeZone("Asia/Tokyo")
        val expected = StrToTimezoneExpression(raw.toDopeType(), "Asia/Tokyo".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support Type toTimeZone extension`() {
        val field = someStringField()
        val expr = field.toTimeZone("Asia/Tokyo")
        val expected = StrToTimezoneExpression(field, "Asia/Tokyo".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }

    @Test
    fun `should support raw toTimeZone extension`() {
        val raw = someString()
        val expr = raw.toTimeZone("Asia/Tokyo".toDopeType())
        val expected = StrToTimezoneExpression(raw.toDopeType(), "Asia/Tokyo".toDopeType())

        assertEquals(expected.toDopeQuery(manager), expr.toDopeQuery(manager))
    }
}
