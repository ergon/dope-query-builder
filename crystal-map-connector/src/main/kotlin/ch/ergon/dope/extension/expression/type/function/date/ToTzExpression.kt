package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.toTimeZone
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisToTzCMNumber")
fun CMJsonField<Number>.toTimeZone(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone, format)

@JvmName("millisToTzCMNumber")
fun CMJsonField<Number>.toTimeZone(timeZone: CMJsonField<String>, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format)

@JvmName("millisToTzTypeCMNumber")
fun TypeExpression<NumberType>.toTimeZone(timeZone: CMJsonField<String>, format: TypeExpression<StringType>? = null) =
    toTimeZone(timeZone.toDopeType(), format)

fun CMJsonField<Number>.toTimeZone(timeZone: String, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format)

fun Number.toTimeZone(timeZone: CMJsonField<String>, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format)

@JvmName("millisToTzCMNumberFormat")
fun CMJsonField<Number>.toTimeZone(timeZone: TypeExpression<StringType>, format: CMJsonField<String>) =
    toDopeType().toTimeZone(timeZone, format.toDopeType())

fun TypeExpression<NumberType>.toTimeZone(timeZone: CMJsonField<String>, format: CMJsonField<String>) =
    toTimeZone(timeZone.toDopeType(), format.toDopeType())

fun CMJsonField<Number>.toTimeZone(timeZone: String, format: CMJsonField<String>) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format.toDopeType())

fun Number.toTimeZone(timeZone: String, format: CMJsonField<String>) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format.toDopeType())

@JvmName("strToTzCMString")
fun CMJsonField<String>.toTimeZone(timeZone: TypeExpression<StringType>) = toDopeType().toTimeZone(timeZone)

@JvmName("strToTzCMStringWithCMString")
fun CMJsonField<String>.toTimeZone(timeZone: CMJsonField<String>) = toDopeType().toTimeZone(timeZone.toDopeType())

@JvmName("strToTzTypeCMString")
fun TypeExpression<StringType>.toTimeZone(timeZone: CMJsonField<String>) = toTimeZone(timeZone.toDopeType())

fun CMJsonField<String>.toTimeZone(timeZone: String) = toDopeType().toTimeZone(timeZone.toDopeType())

fun String.toTimeZone(timeZone: CMJsonField<String>) = toDopeType().toTimeZone(timeZone.toDopeType())
