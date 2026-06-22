package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ClockUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponent
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateDiffStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateFormatStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DatePartStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateRangeStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateTruncStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType
import ch.ergon.dope.resolvable.expression.type.function.date.MillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.MillisToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowLocalExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowStringExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.NowUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToTimezoneExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StrToUtcExpression
import ch.ergon.dope.resolvable.expression.type.function.date.StringToMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayMillisExpression
import ch.ergon.dope.resolvable.expression.type.function.date.WeekDayStrExpression

internal fun ExpressionResolver.resolveDateFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is ClockLocalExpression -> dateToStringOnNow(expression.format, timeZone = null)
        is ClockStringExpression -> dateToStringOnNow(expression.format, timeZone = null)
        is ClockUtcExpression -> dateToStringOnNow(expression.format, timeZone = "UTC")
        is ClockTimezoneExpression -> dateToStringOnNowInZone(expression.timeZone, expression.format)
        is ClockMillisExpression -> fragment("{ \"\$toLong\": \"\$\$NOW\" }")

        is NowLocalExpression -> dateToStringOnNow(expression.format, timeZone = null)
        is NowStringExpression -> dateToStringOnNow(expression.format, timeZone = null)
        is NowUtcExpression -> dateToStringOnNow(expression.format, timeZone = "UTC")
        is NowTimezoneExpression -> dateToStringOnNowInZone(expression.timeZone, expression.format)
        is NowMillisExpression -> fragment("{ \"\$toLong\": \"\$\$NOW\" }")

        is DateAddMillisExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val date = render(expression.date)
            val amount = render(expression.increment)
            fragment(
                "{ \"\$toLong\": { \"\$dateAdd\": { \"startDate\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }, " +
                    "\"unit\": \"$unit\", \"amount\": ${amount.queryString} } } }",
            )
        }

        is DateAddStrExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val date = render(expression.date)
            val amount = render(expression.increment)
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$dateAdd\": { \"startDate\": " +
                    "{ \"\$dateFromString\": { \"dateString\": ${date.queryString} } }, " +
                    "\"unit\": \"$unit\", \"amount\": ${amount.queryString} } } } }",
            )
        }

        is DateDiffMillisExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val endDate = render(expression.date)
            val startDate = render(expression.other)
            fragment(
                "{ \"\$dateDiff\": { \"startDate\": { \"\$toDate\": { \"\$toLong\": ${startDate.queryString} } }, " +
                    "\"endDate\": { \"\$toDate\": { \"\$toLong\": ${endDate.queryString} } }, \"unit\": \"$unit\" } }",
            )
        }

        is DateDiffStrExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val endDate = render(expression.date)
            val startDate = render(expression.other)
            fragment(
                "{ \"\$dateDiff\": { \"startDate\": { \"\$dateFromString\": { \"dateString\": ${startDate.queryString} } }, " +
                    "\"endDate\": { \"\$dateFromString\": { \"dateString\": ${endDate.queryString} } }, " +
                    "\"unit\": \"$unit\" } }",
            )
        }

        is DateTruncMillisExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val date = render(expression.date)
            fragment(
                "{ \"\$toLong\": { \"\$dateTrunc\": { \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }, " +
                    "\"unit\": \"$unit\" } } }",
            )
        }

        is DateTruncStrExpression -> mapDateUnit(expression.dateUnit)?.let { unit ->
            val date = render(expression.date)
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$dateTrunc\": { \"date\": " +
                    "{ \"\$dateFromString\": { \"dateString\": ${date.queryString} } }, \"unit\": \"$unit\" } } } }",
            )
        }

        is DatePartMillisExpression -> mapDateComponent(expression.component)?.let { componentOp ->
            val date = render(expression.date)
            val timeZone = expression.timeZone?.let { render(it) }
            val timeZoneField = timeZone?.let { ", \"timezone\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"$componentOp\": { \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }$timeZoneField } }",
            )
        }

        is DatePartStrExpression -> mapDateComponent(expression.component)?.let { componentOp ->
            val date = render(expression.date)
            fragment(
                "{ \"$componentOp\": { \"\$dateFromString\": { \"dateString\": ${date.queryString} } } }",
            )
        }

        is MillisExpression -> {
            val date = render(expression.date)
            fragment(
                "{ \"\$toLong\": { \"\$dateFromString\": { \"dateString\": ${date.queryString} } } }",
            )
        }

        is StringToMillisExpression -> {
            val date = render(expression.date)
            val format = expression.format?.let { render(it) }
            val formatField = format?.let { ", \"format\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"\$toLong\": { \"\$dateFromString\": { \"dateString\": ${date.queryString}$formatField } } }",
            )
        }

        is MillisToStringExpression -> {
            val date = render(expression.date)
            val format = expression.format?.let { render(it) }
            val formatField = format?.let { ", \"format\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }$formatField } }",
            )
        }

        is DateFormatStrExpression -> {
            val date = render(expression.date)
            val format = render(expression.format)
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$dateFromString\": { \"dateString\": ${date.queryString} } }, " +
                    "\"format\": ${format.queryString} } }",
            )
        }

        is MillisToTimezoneExpression -> {
            val date = render(expression.date)
            val timeZone = render(expression.timeZone)
            val format = expression.format?.let { render(it) }
            val formatField = format?.let { ", \"format\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }$formatField, " +
                    "\"timezone\": ${timeZone.queryString} } }",
            )
        }

        is StrToTimezoneExpression -> {
            val date = render(expression.date)
            val timeZone = render(expression.timeZone)
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$dateFromString\": { \"dateString\": ${date.queryString} } }, " +
                    "\"timezone\": ${timeZone.queryString} } }",
            )
        }

        is MillisToUtcExpression -> {
            val date = render(expression.date)
            val format = expression.format?.let { render(it) }
            val formatField = format?.let { ", \"format\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }$formatField, " +
                    "\"timezone\": \"UTC\" } }",
            )
        }

        is StrToUtcExpression -> {
            val date = render(expression.date)
            fragment(
                "{ \"\$dateToString\": { \"date\": { \"\$dateFromString\": { \"dateString\": ${date.queryString} } }, " +
                    "\"timezone\": \"UTC\" } }",
            )
        }

        is DateRangeMillisExpression -> mapDateUnit(expression.interval)?.let { unit ->
            val startDate = render(expression.startDate)
            val endDate = render(expression.endDate)
            val increment = expression.increment?.let { render(it) }
            val incrementQueryString = increment?.queryString ?: "1"
            fragment(
                "{ \"\$let\": { \"vars\": { \"start\": { \"\$toDate\": { \"\$toLong\": ${startDate.queryString} } }, " +
                    "\"end\": { \"\$toDate\": { \"\$toLong\": ${endDate.queryString} } } }, " +
                    "\"in\": { \"\$map\": { \"input\": { \"\$range\": [ 0, { \"\$toInt\": { \"\$ceil\": { \"\$divide\": " +
                    "[ { \"\$dateDiff\": { \"startDate\": \"\$\$start\", \"endDate\": \"\$\$end\", \"unit\": \"$unit\" } }, " +
                    "$incrementQueryString ] } } } ] }, \"as\": \"i\", \"in\": { \"\$toLong\": { \"\$dateAdd\": " +
                    "{ \"startDate\": \"\$\$start\", \"unit\": \"$unit\", \"amount\": { \"\$multiply\": " +
                    "[ \"\$\$i\", $incrementQueryString ] } } } } } } } }",
            )
        }

        is DateRangeStrExpression -> mapDateUnit(expression.interval)?.let { unit ->
            val startDate = render(expression.startDate)
            val endDate = render(expression.endDate)
            val increment = expression.increment?.let { render(it) }
            val incrementQueryString = increment?.queryString ?: "1"
            fragment(
                "{ \"\$let\": { \"vars\": { \"start\": { \"\$dateFromString\": { \"dateString\": ${startDate.queryString} } }, " +
                    "\"end\": { \"\$dateFromString\": { \"dateString\": ${endDate.queryString} } } }, " +
                    "\"in\": { \"\$map\": { \"input\": { \"\$range\": [ 0, { \"\$toInt\": { \"\$ceil\": { \"\$divide\": " +
                    "[ { \"\$dateDiff\": { \"startDate\": \"\$\$start\", \"endDate\": \"\$\$end\", \"unit\": \"$unit\" } }, " +
                    "$incrementQueryString ] } } } ] }, \"as\": \"i\", \"in\": { \"\$dateToString\": { \"date\": " +
                    "{ \"\$dateAdd\": { \"startDate\": \"\$\$start\", \"unit\": \"$unit\", \"amount\": { \"\$multiply\": " +
                    "[ \"\$\$i\", $incrementQueryString ] } } } } } } } } }",
            )
        }

        is WeekDayMillisExpression -> {
            val date = render(expression.date)
            val timeZone = expression.timeZone?.let { render(it) }
            val timeZoneField = timeZone?.let { ", \"timezone\": ${it.queryString}" } ?: ""
            fragment(
                "{ \"\$arrayElemAt\": [ $weekdayNameArray, { \"\$subtract\": [ { \"\$dayOfWeek\": " +
                    "{ \"date\": { \"\$toDate\": { \"\$toLong\": ${date.queryString} } }$timeZoneField } }, 1 ] } ] }",
            )
        }

        is WeekDayStrExpression -> {
            val date = render(expression.date)
            fragment(
                "{ \"\$arrayElemAt\": [ $weekdayNameArray, { \"\$subtract\": [ { \"\$dayOfWeek\": " +
                    "{ \"\$dateFromString\": { \"dateString\": ${date.queryString} } } }, 1 ] } ] }",
            )
        }

        else -> null
    }

private const val weekdayNameArray =
    "[ \"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\" ]"

private fun ExpressionResolver.dateToStringOnNow(
    format: Expression<*>?,
    timeZone: String?,
): MongoDopeQuery.ExpressionFragment {
    val formatFragment = format?.let { render(it) }
    val formatField = formatFragment?.let { ", \"format\": ${it.queryString}" } ?: ""
    val timeZoneField = timeZone?.let { ", \"timezone\": \"$it\"" } ?: ""
    return fragment(
        "{ \"\$dateToString\": { \"date\": \"\$\$NOW\"$formatField$timeZoneField } }",
    )
}

private fun ExpressionResolver.dateToStringOnNowInZone(
    timeZone: Expression<*>,
    format: Expression<*>?,
): MongoDopeQuery.ExpressionFragment {
    val timeZoneFragment = render(timeZone)
    val formatFragment = format?.let { render(it) }
    val formatField = formatFragment?.let { ", \"format\": ${it.queryString}" } ?: ""
    return fragment(
        "{ \"\$dateToString\": { \"date\": \"\$\$NOW\"$formatField, \"timezone\": ${timeZoneFragment.queryString} } }",
    )
}

private fun mapDateUnit(dateUnit: DateUnit): String? =
    when (dateUnit) {
        DateUnitType.YEAR -> "year"
        DateUnitType.QUARTER -> "quarter"
        DateUnitType.MONTH -> "month"
        DateUnitType.WEEK -> "week"
        DateUnitType.DAY -> "day"
        DateUnitType.HOUR -> "hour"
        DateUnitType.MINUTE -> "minute"
        DateUnitType.SECOND -> "second"
        DateUnitType.MILLISECOND -> "millisecond"
        DateUnitType.MILLENNIUM, DateUnitType.CENTURY, DateUnitType.DECADE -> null
    }

private fun mapDateComponent(component: DateComponent): String? =
    when (component) {
        DateUnitType.YEAR -> "\$year"
        DateUnitType.MONTH -> "\$month"
        DateUnitType.DAY -> "\$dayOfMonth"
        DateUnitType.HOUR -> "\$hour"
        DateUnitType.MINUTE -> "\$minute"
        DateUnitType.SECOND -> "\$second"
        DateUnitType.MILLISECOND -> "\$millisecond"
        DateComponentType.ISO_YEAR -> "\$isoWeekYear"
        DateComponentType.ISO_WEEK -> "\$isoWeek"
        DateComponentType.DAY_OF_YEAR -> "\$dayOfYear"
        DateComponentType.DAY_OF_WEEK -> "\$dayOfWeek"
        else -> null
    }
