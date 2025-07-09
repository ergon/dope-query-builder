package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.toUtcDate
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisToUtcCMNumber")
fun CMJsonField<Number>.toUtcDate(format: TypeExpression<StringType>? = null) = toDopeType().toUtcDate(format)

fun CMJsonField<Number>.toUtcDate(format: CMJsonField<String>) = toDopeType().toUtcDate(format.toDopeType())

@JvmName("millisToUtcTypeCMNumber")
fun TypeExpression<NumberType>.toUtcDate(format: CMJsonField<String>) = toUtcDate(format.toDopeType())

fun CMJsonField<Number>.toUtcDate(format: String) = toDopeType().toUtcDate(format.toDopeType())

fun Number.toUtcDate(format: CMJsonField<String>) = toDopeType().toUtcDate(format.toDopeType())

fun CMJsonField<String>.toUtcDate() = toDopeType().toUtcDate()
