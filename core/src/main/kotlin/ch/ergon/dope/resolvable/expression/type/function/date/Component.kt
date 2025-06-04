package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

sealed interface DateComponent : TypeExpression<StringType>
sealed interface DateUnit : DateComponent

object Millennium : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "MILLENNIUM".toDopeType().toDopeQuery(manager)
}

object Century : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "CENTURY".toDopeType().toDopeQuery(manager)
}

object Decade : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "DECADE".toDopeType().toDopeQuery(manager)
}

object Year : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "YEAR".toDopeType().toDopeQuery(manager)
}

object IsoYear : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "ISO_YEAR".toDopeType().toDopeQuery(manager)
}

object Quarter : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "QUARTER".toDopeType().toDopeQuery(manager)
}

object Month : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "MONTH".toDopeType().toDopeQuery(manager)
}

object Week : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "WEEK".toDopeType().toDopeQuery(manager)
}

object IsoWeek : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "ISO_WEEK".toDopeType().toDopeQuery(manager)
}

object Day : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "DAY".toDopeType().toDopeQuery(manager)
}

object DayOfYear : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "DAY_OF_YEAR".toDopeType().toDopeQuery(manager)
}

object DayOfWeek : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "DAY_OF_WEEK".toDopeType().toDopeQuery(manager)
}

object Hour : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "HOUR".toDopeType().toDopeQuery(manager)
}

object Minute : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "MINUTE".toDopeType().toDopeQuery(manager)
}

object Second : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "SECOND".toDopeType().toDopeQuery(manager)
}

object Millisecond : DateUnit {
    override fun toDopeQuery(manager: DopeQueryManager) = "MILLISECOND".toDopeType().toDopeQuery(manager)
}

object Timezone : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE".toDopeType().toDopeQuery(manager)
}

object TimezoneHour : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE_HOUR".toDopeType().toDopeQuery(manager)
}

object TimezoneMinute : DateComponent {
    override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE_MINUTE".toDopeType().toDopeQuery(manager)
}
