package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

sealed interface DateComponent : TypeExpression<StringType>
sealed interface DateUnit : DateComponent

enum class DateUnitType(private val queryString: String) : DateUnit {
    MILLENNIUM("MILLENNIUM"),
    CENTURY("CENTURY"),
    DECADE("DECADE"),
    YEAR("YEAR"),
    QUARTER("QUARTER"),
    MONTH("MONTH"),
    WEEK("WEEK"),
    DAY("DAY"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND"),
    MILLISECOND("MILLISECOND"),
    ;

    override fun toDopeQuery(manager: DopeQueryManager) =
        queryString.toDopeType().toDopeQuery(manager)
}

enum class DateComponentType(private val queryString: String) : DateComponent {
    ISO_YEAR("ISO_YEAR"),
    ISO_WEEK("ISO_WEEK"),
    DAY_OF_YEAR("DAY_OF_YEAR"),
    DAY_OF_WEEK("DAY_OF_WEEK"),
    TIMEZONE("TIMEZONE"),
    TIMEZONE_HOUR("TIMEZONE_HOUR"),
    TIMEZONE_MINUTE("TIMEZONE_MINUTE"),
    ;

    override fun toDopeQuery(manager: DopeQueryManager) =
        queryString.toDopeType().toDopeQuery(manager)
}
