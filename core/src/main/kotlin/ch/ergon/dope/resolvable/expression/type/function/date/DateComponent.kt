package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

enum class DateComponent(private val queryString: String) : Resolvable {
    MILLENNIUM("MILLENNIUM"),
    CENTURY("CENTURY"),
    DECADE("DECADE"),
    YEAR("YEAR"),
    ISO_YEAR("ISO_YEAR"),
    QUARTER("QUARTER"),
    MONTH("MONTH"),
    WEEK("WEEK"),
    ISO_WEEK("ISO_WEEK"),
    DAY("DAY"),
    DAY_OF_YEAR("DAY_OF_YEAR"),
    DOY("DOY"),
    DAY_OF_WEEK("DAY_OF_WEEK"),
    DOW("DOW"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND"),
    MILLISECOND("MILLISECOND"),
    TIMEZONE("TIMEZONE"),
    TIMEZONE_HOUR("TIMEZONE_HOUR"),
    TIMEZONE_MINUTE("TIMEZONE_MINUTE"),
    ;

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery = DopeQuery("\"$queryString\"")
}
