package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

sealed interface ArithmeticComponent : TypeExpression<StringType>
sealed interface ExtractionComponent : TypeExpression<StringType>
sealed interface TruncationComponent : TypeExpression<StringType>

object Component {
    object Millennium : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "MILLENNIUM".toDopeType().toDopeQuery(manager)
    }

    object Century : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "CENTURY".toDopeType().toDopeQuery(manager)
    }

    object Decade : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "DECADE".toDopeType().toDopeQuery(manager)
    }

    object Year : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "YEAR".toDopeType().toDopeQuery(manager)
    }

    object IsoYear : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "ISO_YEAR".toDopeType().toDopeQuery(manager)
    }

    object Quarter : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "QUARTER".toDopeType().toDopeQuery(manager)
    }

    object Month : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "MONTH".toDopeType().toDopeQuery(manager)
    }

    object Week : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "WEEK".toDopeType().toDopeQuery(manager)
    }

    object IsoWeek : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "ISO_WEEK".toDopeType().toDopeQuery(manager)
    }

    object Day : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "DAY".toDopeType().toDopeQuery(manager)
    }

    object DayOfYear : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "DAY_OF_YEAR".toDopeType().toDopeQuery(manager)
    }

    object DayOfWeek : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "DAY_OF_WEEK".toDopeType().toDopeQuery(manager)
    }

    object Hour : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "HOUR".toDopeType().toDopeQuery(manager)
    }

    object Minute : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "MINUTE".toDopeType().toDopeQuery(manager)
    }

    object Second : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "SECOND".toDopeType().toDopeQuery(manager)
    }

    object Millisecond : ArithmeticComponent, ExtractionComponent, TruncationComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "MILLISECOND".toDopeType().toDopeQuery(manager)
    }

    object Timezone : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE".toDopeType().toDopeQuery(manager)
    }

    object TimezoneHour : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE_HOUR".toDopeType().toDopeQuery(manager)
    }

    object TimezoneMinute : ExtractionComponent {
        override fun toDopeQuery(manager: DopeQueryManager) = "TIMEZONE_MINUTE".toDopeType().toDopeQuery(manager)
    }
}
