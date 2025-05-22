package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.nowStringInZone
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun nowStringInZone(timeZone: CMJsonField<String>, format: CMJsonField<String>? = null) = nowStringInZone(
    timeZone.toDopeType(),
    format?.toDopeType(),
)

fun nowStringInZone(timeZone: CMJsonField<String>, format: String) = nowStringInZone(timeZone.toDopeType(), format.toDopeType())

fun nowStringInZone(timeZone: String, format: CMJsonField<String>) = nowStringInZone(timeZone.toDopeType(), format.toDopeType())

fun nowStringInZone(timeZone: CMJsonField<String>, format: TypeExpression<StringType>) = nowStringInZone(timeZone.toDopeType(), format)

fun nowStringInZone(timeZone: TypeExpression<StringType>, format: CMJsonField<String>) = nowStringInZone(timeZone, format.toDopeType())
