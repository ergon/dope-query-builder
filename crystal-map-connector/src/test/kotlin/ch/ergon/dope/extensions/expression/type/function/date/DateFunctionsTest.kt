package ch.ergon.dope.extensions.expression.type.function.date

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.type.function.date.clockString
import ch.ergon.dope.extension.expression.type.function.date.dateRangeBy
import ch.ergon.dope.extension.expression.type.function.date.differenceIn
import ch.ergon.dope.extension.expression.type.function.date.extractDateComponent
import ch.ergon.dope.extension.expression.type.function.date.extractWeekdayName
import ch.ergon.dope.extension.expression.type.function.date.formatDate
import ch.ergon.dope.extension.expression.type.function.date.formattedClockIn
import ch.ergon.dope.extension.expression.type.function.date.localClockString
import ch.ergon.dope.extension.expression.type.function.date.localNowString
import ch.ergon.dope.extension.expression.type.function.date.nowString
import ch.ergon.dope.extension.expression.type.function.date.nowStringInZone
import ch.ergon.dope.extension.expression.type.function.date.plusDateComponent
import ch.ergon.dope.extension.expression.type.function.date.toDurationMillis
import ch.ergon.dope.extension.expression.type.function.date.toDurationString
import ch.ergon.dope.extension.expression.type.function.date.toEpochMillis
import ch.ergon.dope.extension.expression.type.function.date.toFormattedDate
import ch.ergon.dope.extension.expression.type.function.date.toMillis
import ch.ergon.dope.extension.expression.type.function.date.toTimeZone
import ch.ergon.dope.extension.expression.type.function.date.toUtcDate
import ch.ergon.dope.extension.expression.type.function.date.truncateTo
import ch.ergon.dope.extension.expression.type.function.date.utcClockString
import ch.ergon.dope.extension.expression.type.function.date.utcNowString
import ch.ergon.dope.helper.DateNumberConverterInstance
import ch.ergon.dope.helper.DateStringConverterInstance
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMConverterNumberField
import ch.ergon.dope.helper.someCMConverterStringField
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someDate
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.function.date.ClockLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateFormatStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.Day
import ch.ergon.dope.resolvable.expression.type.function.date.DurationToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.Hour
import ch.ergon.dope.resolvable.expression.type.function.date.MillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.Minute
import ch.ergon.dope.resolvable.expression.type.function.date.Month
import ch.ergon.dope.resolvable.expression.type.function.date.NowLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StringToDurationExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StringToMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.Week
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.extractDateComponent
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFunctionsTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ClockLocalExpression with CM string`() {
        val format = someCMStringField()
        val expected = ClockLocalExpression(format.toDopeType())

        val actual = localClockString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockStrExpression with CM string`() {
        val format = someCMStringField()
        val expected = ClockStringExpression(format.toDopeType())

        val actual = clockString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with CM string`() {
        val tz = someCMStringField()
        val expected = ClockTimezoneExpression(tz.toDopeType())

        val actual = formattedClockIn(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with CM string and CM string`() {
        val tz = someCMStringField()
        val format = someCMStringField()
        val expected = ClockTimezoneExpression(tz.toDopeType(), format.toDopeType())

        val actual = formattedClockIn(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with string and CM string`() {
        val tz = someString()
        val format = someCMStringField()
        val expected = ClockTimezoneExpression(tz.toDopeType(), format.toDopeType())

        val actual = formattedClockIn(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with CM string and string`() {
        val tz = someCMStringField()
        val format = someString()
        val expected = ClockTimezoneExpression(tz.toDopeType(), format.toDopeType())

        val actual = formattedClockIn(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with CM string and type`() {
        val tz = someCMStringField()
        val format = someStringField()
        val expected = ClockTimezoneExpression(tz.toDopeType(), format)

        val actual = formattedClockIn(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockTimezone with type and CM string`() {
        val tz = someStringField()
        val format = someCMStringField()
        val expected = ClockTimezoneExpression(tz, format.toDopeType())

        val actual = formattedClockIn(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ClockUtc with CM string`() {
        val format = someCMStringField()
        val expected = ClockUtcExpression(format.toDopeType())

        val actual = utcClockString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM string CM number`() {
        val date = someCMStringField()
        val increment = someCMNumberField()
        val expected = DateAddStrExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with type and CM number`() {
        val date = someStringField()
        val increment = someCMNumberField()
        val expected = DateAddStrExpression(date, increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM string type`() {
        val date = someCMStringField()
        val increment = someNumberField()
        val expected = DateAddStrExpression(date.toDopeType(), increment, Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM string number`() {
        val date = someCMStringField()
        val increment = someNumber()
        val expected = DateAddStrExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with string CM number`() {
        val date = someString()
        val increment = someCMNumberField()
        val expected = DateAddStrExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM number CM number`() {
        val date = someCMNumberField()
        val increment = someCMNumberField()
        val expected = DateAddMillisExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with type CM number`() {
        val date = someNumberField()
        val increment = someCMNumberField()
        val expected = DateAddMillisExpression(date, increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM number type`() {
        val date = someCMNumberField()
        val increment = someNumberField()
        val expected = DateAddMillisExpression(date.toDopeType(), increment, Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with CM number number`() {
        val date = someCMNumberField()
        val increment = someNumber()
        val expected = DateAddMillisExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateAdd with number CM number`() {
        val date = someNumber()
        val increment = someCMNumberField()
        val expected = DateAddMillisExpression(date.toDopeType(), increment.toDopeType(), Day)

        val actual = date.plusDateComponent(increment, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM number and CM number`() {
        val date = someCMNumberField()
        val other = someCMNumberField()
        val component = Minute
        val expected = DateDiffMillisExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM number and number`() {
        val date = someCMNumberField()
        val other = someNumber()
        val component = Minute
        val expected = DateDiffMillisExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with number and CM number`() {
        val date = someNumber()
        val other = someCMNumberField()
        val component = Minute
        val expected = DateDiffMillisExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with type and CM number`() {
        val date = someNumberField()
        val other = someCMNumberField()
        val component = Month
        val expected = DateDiffMillisExpression(date, other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM number and type`() {
        val date = someCMNumberField()
        val other = someNumberField()
        val component = Week
        val expected = DateDiffMillisExpression(date.toDopeType(), other, component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM converter number`() {
        val date = someCMConverterNumberField()
        val other = Date()
        val component = Week
        val expected = DateDiffMillisExpression(date.toDopeType(), DateNumberConverterInstance.write(other)!!.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM string and CM string`() {
        val date = someCMStringField()
        val other = someCMStringField()
        val component = Week
        val expected = DateDiffStrExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM string and string`() {
        val date = someCMStringField()
        val other = someString()
        val component = Hour
        val expected = DateDiffStrExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with string and CM string`() {
        val date = someString()
        val other = someCMStringField()
        val component = Hour
        val expected = DateDiffStrExpression(date.toDopeType(), other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with type and CM string`() {
        val date = someStringField()
        val other = someCMStringField()
        val component = Hour
        val expected = DateDiffStrExpression(date, other.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM string and type`() {
        val date = someCMStringField()
        val other = someStringField()
        val component = Hour
        val expected = DateDiffStrExpression(date.toDopeType(), other, component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateDiff with CM converter string`() {
        val date = someCMConverterStringField()
        val other = someDate()
        val component = Week
        val expected = DateDiffStrExpression(date.toDopeType(), DateStringConverterInstance.write(other)!!.toDopeType(), component)

        val actual = date.differenceIn(other, component)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateFormat with CM string and CM string`() {
        val date = someCMStringField()
        val other = someCMStringField()
        val expected = DateFormatStrExpression(date.toDopeType(), other.toDopeType())

        val actual = date.formatDate(other)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateFormat with type and CM string`() {
        val date = someStringField()
        val other = someCMStringField()
        val expected = DateFormatStrExpression(date, other.toDopeType())

        val actual = date.formatDate(other)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateFormat with CM string and type`() {
        val date = someCMStringField()
        val other = someStringField()
        val expected = DateFormatStrExpression(date.toDopeType(), other)

        val actual = date.formatDate(other)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateFormat with CM string and string`() {
        val date = someCMStringField()
        val other = someString()
        val expected = DateFormatStrExpression(date.toDopeType(), other.toDopeType())

        val actual = date.formatDate(other)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateFormat with string and CM string`() {
        val date = someString()
        val other = someCMStringField()
        val expected = DateFormatStrExpression(date.toDopeType(), other.toDopeType())

        val actual = date.formatDate(other)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with CM number`() {
        val date = someCMNumberField()
        val expected = DatePartMillisExpression(date.toDopeType(), Day)

        val actual = date.extractDateComponent(Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with CM number and type`() {
        val date = someCMNumberField()
        val tz = someStringField()
        val expected = DatePartMillisExpression(date.toDopeType(), Day, tz)

        val actual = date.extractDateComponent(Day, tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with CM number and CM string`() {
        val date = someCMNumberField()
        val tz = someCMStringField()
        val expected = DatePartMillisExpression(date.toDopeType(), Day, tz.toDopeType())

        val actual = date.extractDateComponent(Day, tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with type and CM string`() {
        val date = someNumberField()
        val tz = someCMStringField()
        val expected = DatePartMillisExpression(date, Day, tz.toDopeType())

        val actual = date.extractDateComponent(Day, tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with CM number and string`() {
        val date = someCMNumberField()
        val tz = someString()
        val expected = DatePartMillisExpression(date.toDopeType(), Day, tz.toDopeType())

        val actual = date.extractDateComponent(Day, tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with number and CM string`() {
        val date = someNumber()
        val tz = someCMStringField()
        val expected = DatePartMillisExpression(date.toDopeType(), Day, tz.toDopeType())

        val actual = date.extractDateComponent(Day, tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with string`() {
        val date = someString()
        val expected = DatePartStrExpression(date.toDopeType(), Day)

        val actual = date.extractDateComponent(Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DatePart with CM string`() {
        val date = someCMStringField()
        val expected = DatePartStrExpression(date.toDopeType(), Day)

        val actual = date.extractDateComponent(Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM number CM number`() {
        val start = someCMNumberField()
        val end = someCMNumberField()
        val expected = DateRangeMillisExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM number type`() {
        val start = someCMNumberField()
        val end = someNumberField()
        val expected = DateRangeMillisExpression(start.toDopeType(), end, Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with type CM number`() {
        val start = someNumberField()
        val end = someCMNumberField()
        val expected = DateRangeMillisExpression(start, end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM number number`() {
        val start = someCMNumberField()
        val end = someNumber()
        val expected = DateRangeMillisExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with number CM number`() {
        val start = someNumber()
        val end = someCMNumberField()
        val expected = DateRangeMillisExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with number, CM number and number`() {
        val start = someNumber()
        val end = someCMNumberField()
        val increment = someNumber()
        val expected = DateRangeMillisExpression(start.toDopeType(), end.toDopeType(), Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM number, CM number and number`() {
        val start = someCMNumberField()
        val end = someCMNumberField()
        val increment = someNumber()
        val expected = DateRangeMillisExpression(start.toDopeType(), end.toDopeType(), Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with type, CM number and number`() {
        val start = someNumberField()
        val end = someCMNumberField()
        val increment = someNumber()
        val expected = DateRangeMillisExpression(start, end.toDopeType(), Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM number, type and number`() {
        val start = someCMNumberField()
        val end = someNumberField()
        val increment = someNumber()
        val expected = DateRangeMillisExpression(start.toDopeType(), end, Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM string CM string`() {
        val start = someCMStringField()
        val end = someCMStringField()
        val expected = DateRangeStrExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with type CM string`() {
        val start = someStringField()
        val end = someCMStringField()
        val expected = DateRangeStrExpression(start, end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM string type`() {
        val start = someCMStringField()
        val end = someStringField()
        val expected = DateRangeStrExpression(start.toDopeType(), end, Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM string string`() {
        val start = someCMStringField()
        val end = someString()
        val expected = DateRangeStrExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with string CM string`() {
        val start = someString()
        val end = someCMStringField()
        val expected = DateRangeStrExpression(start.toDopeType(), end.toDopeType(), Day)

        val actual = start.dateRangeBy(end, Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM string, CM string and number`() {
        val start = someCMStringField()
        val end = someCMStringField()
        val increment = someNumber()
        val expected = DateRangeStrExpression(start.toDopeType(), end.toDopeType(), Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with type, CM string and number`() {
        val start = someStringField()
        val end = someCMStringField()
        val increment = someNumber()
        val expected = DateRangeStrExpression(start, end.toDopeType(), Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateRange with CM string, type and number`() {
        val start = someCMStringField()
        val end = someStringField()
        val increment = someNumber()
        val expected = DateRangeStrExpression(start.toDopeType(), end, Day, increment.toDopeType())

        val actual = start.dateRangeBy(end, Day, increment)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateTrunc with CM number`() {
        val date = someCMNumberField()
        val expected = DateTruncMillisExpression(date.toDopeType(), Day)

        val actual = date.truncateTo(Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DateTrunc with CM string`() {
        val date = someCMStringField()
        val expected = DateTruncStrExpression(date.toDopeType(), Day)

        val actual = date.truncateTo(Day)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support DurationToString with CM number`() {
        val date = someCMNumberField()
        val expected = DurationToStringExpression(date.toDopeType())

        val actual = date.toDurationString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support Millis`() {
        val date = someCMStringField()
        val expected = MillisExpression(date.toDopeType())

        val actual = date.toMillis()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisTo with CM number`() {
        val date = someCMNumberField()
        val expected = MillisToStringExpression(date.toDopeType())

        val actual = date.toFormattedDate()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisTo with CM number and CM string`() {
        val date = someCMNumberField()
        val format = someCMStringField()
        val expected = MillisToStringExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toFormattedDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisTo with type and CM string`() {
        val date = someNumberField()
        val format = someCMStringField()
        val expected = MillisToStringExpression(date, format.toDopeType())

        val actual = date.toFormattedDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisTo with CM number and string`() {
        val date = someCMNumberField()
        val format = someString()
        val expected = MillisToStringExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toFormattedDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisTo with number and CM string`() {
        val date = someNumber()
        val format = someCMStringField()
        val expected = MillisToStringExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toFormattedDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowLocal with CM string`() {
        val format = someCMStringField()
        val expected = NowLocalExpression(format.toDopeType())

        val actual = localNowString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowString with CM string`() {
        val format = someCMStringField()
        val expected = NowStringExpression(format.toDopeType())

        val actual = nowString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowTimezone with CM string`() {
        val tz = someCMStringField()
        val expected = NowTimezoneExpression(tz.toDopeType())

        val actual = nowStringInZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowTimezone with CM string and string`() {
        val tz = someCMStringField()
        val format = someString()
        val expected = NowTimezoneExpression(tz.toDopeType(), format.toDopeType())

        val actual = nowStringInZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowTimezone with string and CM string`() {
        val tz = someString()
        val format = someCMStringField()
        val expected = NowTimezoneExpression(tz.toDopeType(), format.toDopeType())

        val actual = nowStringInZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowTimezone with CM string and type`() {
        val tz = someCMStringField()
        val format = someStringField()
        val expected = NowTimezoneExpression(tz.toDopeType(), format)

        val actual = nowStringInZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support NowTimezone with type and CM string`() {
        val tz = someStringField()
        val format = someCMStringField()
        val expected = NowTimezoneExpression(tz, format.toDopeType())

        val actual = nowStringInZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support UtcNow with CM string and string`() {
        val format = someCMStringField()
        val expected = NowUtcExpression(format.toDopeType())

        val actual = utcNowString(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToDuration with CM string`() {
        val duration = someCMStringField()
        val expected = StringToDurationExpression(duration.toDopeType())

        val actual = duration.toDurationMillis()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrTo with CM string`() {
        val date = someCMStringField()
        val expected = StringToMillisExpression(date.toDopeType())

        val actual = date.toEpochMillis()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrTo with CM string and CM string`() {
        val date = someCMStringField()
        val format = someCMStringField()
        val expected = StringToMillisExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toEpochMillis(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrTo with type and CM string`() {
        val date = someStringField()
        val format = someCMStringField()
        val expected = StringToMillisExpression(date, format.toDopeType())

        val actual = date.toEpochMillis(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrTo with CM string and string`() {
        val date = someCMStringField()
        val format = someString()
        val expected = StringToMillisExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toEpochMillis(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrTo with string and CM string`() {
        val date = someString()
        val format = someCMStringField()
        val expected = StringToMillisExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toEpochMillis(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with CM number type`() {
        val date = someCMNumberField()
        val tz = someStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz)

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with CM number CM string`() {
        val date = someCMNumberField()
        val tz = someCMStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with type CM string`() {
        val date = someNumberField()
        val tz = someCMStringField()
        val expected = MillisToTimezoneExpression(date, tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with CM number string`() {
        val date = someCMNumberField()
        val tz = someString()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with number CM string`() {
        val date = someNumber()
        val tz = someCMStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with CM number, type and CM string`() {
        val date = someCMNumberField()
        val tz = someStringField()
        val format = someCMStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz, format.toDopeType())

        val actual = date.toTimeZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with type, CM string and CM string`() {
        val date = someNumberField()
        val tz = someCMStringField()
        val format = someCMStringField()
        val expected = MillisToTimezoneExpression(date, tz.toDopeType(), format.toDopeType())

        val actual = date.toTimeZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with CM number, string and CM string`() {
        val date = someCMNumberField()
        val tz = someString()
        val format = someCMStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz.toDopeType(), format.toDopeType())

        val actual = date.toTimeZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToTimezone with number, string and CM string`() {
        val date = someNumber()
        val tz = someString()
        val format = someCMStringField()
        val expected = MillisToTimezoneExpression(date.toDopeType(), tz.toDopeType(), format.toDopeType())

        val actual = date.toTimeZone(tz, format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToTimezone with CM string type`() {
        val date = someCMStringField()
        val tz = someStringField()
        val expected = StrToTimezoneExpression(date.toDopeType(), tz)

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToTimezone with CM string CM string`() {
        val date = someCMStringField()
        val tz = someCMStringField()
        val expected = StrToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToTimezone with type CM string`() {
        val date = someStringField()
        val tz = someCMStringField()
        val expected = StrToTimezoneExpression(date, tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToTimezone with CM string string`() {
        val date = someCMStringField()
        val tz = someString()
        val expected = StrToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support StrToTimezone with string CM string`() {
        val date = someString()
        val tz = someCMStringField()
        val expected = StrToTimezoneExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.toTimeZone(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with CM number`() {
        val date = someCMNumberField()
        val expected = MillisToUtcExpression(date.toDopeType())

        val actual = date.toUtcDate()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with CM number and CM string`() {
        val date = someCMNumberField()
        val format = someCMStringField()
        val expected = MillisToUtcExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toUtcDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with type and CM string`() {
        val date = someNumberField()
        val format = someCMStringField()
        val expected = MillisToUtcExpression(date, format.toDopeType())

        val actual = date.toUtcDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with CM number and string`() {
        val date = someCMNumberField()
        val format = someString()
        val expected = MillisToUtcExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toUtcDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with number and CM string`() {
        val date = someNumber()
        val format = someCMStringField()
        val expected = MillisToUtcExpression(date.toDopeType(), format.toDopeType())

        val actual = date.toUtcDate(format)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support MillisToUtc with CM string`() {
        val date = someCMStringField()
        val expected = StrToUtcExpression(date.toDopeType())

        val actual = date.toUtcDate()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with CM number`() {
        val date = someCMNumberField()
        val expected = WeekDayMillisExpression(date.toDopeType())

        val actual = date.extractWeekdayName()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with CM number and CM string`() {
        val date = someCMNumberField()
        val tz = someCMStringField()
        val expected = WeekDayMillisExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.extractWeekdayName(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with type and CM string`() {
        val date = someNumberField()
        val tz = someCMStringField()
        val expected = WeekDayMillisExpression(date, tz.toDopeType())

        val actual = date.extractWeekdayName(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with CM number and string`() {
        val date = someCMNumberField()
        val tz = someString()
        val expected = WeekDayMillisExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.extractWeekdayName(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with number and CM string`() {
        val date = someNumber()
        val tz = someCMStringField()
        val expected = WeekDayMillisExpression(date.toDopeType(), tz.toDopeType())

        val actual = date.extractWeekdayName(tz)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support WeekDay with CM string`() {
        val date = someCMStringField()
        val expected = WeekDayStrExpression(date.toDopeType())

        val actual = date.extractWeekdayName()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
