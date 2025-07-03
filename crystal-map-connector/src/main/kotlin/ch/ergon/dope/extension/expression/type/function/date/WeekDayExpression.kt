package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.extractWeekdayName
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisWeekdayCMNumber")
fun CMJsonField<Number>.extractWeekdayName(timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractWeekdayName(timeZone)

fun CMJsonField<Number>.extractWeekdayName(timeZone: CMJsonField<String>) =
    toDopeType().extractWeekdayName(timeZone)

@JvmName("millisWeekdayTypeCMNumber")
fun TypeExpression<NumberType>.extractWeekdayName(timeZone: CMJsonField<String>) =
    extractWeekdayName(timeZone.toDopeType())

fun CMJsonField<Number>.extractWeekdayName(timeZone: String) =
    toDopeType().extractWeekdayName(timeZone.toDopeType())

fun Number.extractWeekdayName(timeZone: CMJsonField<String>) =
    toDopeType().extractWeekdayName(timeZone.toDopeType())

fun CMJsonField<String>.extractWeekdayName() = toDopeType().extractWeekdayName()
