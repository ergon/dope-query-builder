package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.toEpochMillis
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("strToMillisCMString")
fun CMJsonField<String>.toEpochMillis(format: TypeExpression<StringType>? = null) = toDopeType().toEpochMillis(format)

fun CMJsonField<String>.toEpochMillis(format: CMJsonField<String>) = toDopeType().toEpochMillis(format.toDopeType())

@JvmName("strToMillisTypeCMString")
fun TypeExpression<StringType>.toEpochMillis(format: CMJsonField<String>) = toEpochMillis(format.toDopeType())

fun CMJsonField<String>.toEpochMillis(format: String) = toDopeType().toEpochMillis(format.toDopeType())

fun String.toEpochMillis(format: CMJsonField<String>) = toDopeType().toEpochMillis(format.toDopeType())
