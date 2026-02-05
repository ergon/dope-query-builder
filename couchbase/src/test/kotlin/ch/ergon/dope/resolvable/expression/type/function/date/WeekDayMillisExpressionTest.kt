package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class WeekDayMillisExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support WEEKDAY_MILLIS with field only`() {
        val underTest = WeekDayMillisExpression(someNumberField())
        val expected = CouchbaseDopeQuery("WEEKDAY_MILLIS(`numberField`)")
        assertEquals(expected, underTest.toDopeQuery(resolver))
    }

    @Test
    fun `should support WEEKDAY_MILLIS with field and tz field`() {
        val underTest = WeekDayMillisExpression(someNumberField(), someStringField())
        val expected = CouchbaseDopeQuery("WEEKDAY_MILLIS(`numberField`, `stringField`)")
        assertEquals(expected, underTest.toDopeQuery(resolver))
    }

    @Test
    fun `should support WEEKDAY_MILLIS with positional tz parameter`() {
        val underTest = WeekDayMillisExpression(someNumberField(), "UTC".asParameter())
        val expected = CouchbaseDopeQuery("WEEKDAY_MILLIS(`numberField`, $1)", DopeParameters(positionalParameters = listOf("UTC")))
        assertEquals(expected, underTest.toDopeQuery(resolver))
    }

    @Test
    fun `should support WEEKDAY_MILLIS with named tz parameter`() {
        val underTest = WeekDayMillisExpression(someNumberField(), "UTC".asParameter("z"))
        val expected = CouchbaseDopeQuery("WEEKDAY_MILLIS(`numberField`, \$z)", DopeParameters(namedParameters = mapOf("z" to "UTC")))
        assertEquals(expected, underTest.toDopeQuery(resolver))
    }

    @Test
    fun `should support extractWeekdayName extension on TypeExpression without tz`() {
        val expr = someNumberField().extractWeekdayName()
        val expected = WeekDayMillisExpression(someNumberField(), null)
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support extractWeekdayName extension on TypeExpression with tz expression`() {
        val expr = someNumberField().extractWeekdayName(someStringField())
        val expected = WeekDayMillisExpression(someNumberField(), someStringField())
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support extractWeekdayName extension on TypeExpression with raw tz`() {
        val expr = someNumberField().extractWeekdayName("Europe/Berlin")
        val expected = WeekDayMillisExpression(someNumberField(), "Europe/Berlin".toDopeType())
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support Number extractWeekdayName extension without tz`() {
        val expr = 123L.extractWeekdayName()
        val expected = WeekDayMillisExpression(123L.toDopeType(), null)
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support Number extractWeekdayName extension with tz expression`() {
        val expr = 123L.extractWeekdayName(someStringField())
        val expected = WeekDayMillisExpression(123L.toDopeType(), someStringField())
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support Number extractWeekdayName extension with raw tz`() {
        val expr = 123L.extractWeekdayName("GMT")
        val expected = WeekDayMillisExpression(123L.toDopeType(), "GMT".toDopeType())
        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }
}
