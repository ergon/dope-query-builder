package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.formattedClockIn
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun formattedClockIn(timeZone: CMJsonField<String>, format: CMJsonField<String>? = null) =
    formattedClockIn(timeZone.toDopeType(), format?.toDopeType())

fun formattedClockIn(timeZone: String, format: CMJsonField<String>) = formattedClockIn(timeZone.toDopeType(), format.toDopeType())

fun formattedClockIn(timeZone: CMJsonField<String>, format: String) = formattedClockIn(timeZone.toDopeType(), format.toDopeType())

fun formattedClockIn(timeZone: CMJsonField<String>, format: TypeExpression<StringType>) = formattedClockIn(timeZone.toDopeType(), format)

fun formattedClockIn(timeZone: TypeExpression<StringType>, format: CMJsonField<String>) = formattedClockIn(timeZone, format.toDopeType())
