package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.toFormattedDate
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisToStrCMNumber")
fun CMJsonField<Number>.toFormattedDate(format: TypeExpression<StringType>? = null) = toDopeType().toFormattedDate(format)

fun CMJsonField<Number>.toFormattedDate(format: CMJsonField<String>) = toDopeType().toFormattedDate(format)

@JvmName("millisToStrTypeCMNumber")
fun TypeExpression<NumberType>.toFormattedDate(format: CMJsonField<String>) = toFormattedDate(format.toDopeType())

fun CMJsonField<Number>.toFormattedDate(format: String) = toDopeType().toFormattedDate(format.toDopeType())

fun Number.toFormattedDate(format: CMJsonField<String>) = toDopeType().toFormattedDate(format.toDopeType())
