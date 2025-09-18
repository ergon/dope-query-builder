package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType

sealed interface DateComponent : TypeExpression<StringType>
sealed interface DateUnit : DateComponent

enum class DateUnitType : DateUnit {
    MILLENNIUM,
    CENTURY,
    DECADE,
    YEAR,
    QUARTER,
    MONTH,
    WEEK,
    DAY,
    HOUR,
    MINUTE,
    SECOND,
    MILLISECOND,
    ;
}

enum class DateComponentType : DateComponent {
    ISO_YEAR,
    ISO_WEEK,
    DAY_OF_YEAR,
    DAY_OF_WEEK,
    TIMEZONE,
    TIMEZONE_HOUR,
    TIMEZONE_MINUTE,
    ;
}
