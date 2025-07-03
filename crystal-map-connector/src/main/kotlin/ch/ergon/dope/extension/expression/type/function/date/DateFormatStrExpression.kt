package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.formatDate
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("strFormatCMString")
fun CMJsonField<String>.formatDate(format: CMJsonField<String>) = toDopeType().formatDate(format.toDopeType())

@JvmName("strFormatTypeCMString")
fun TypeExpression<StringType>.formatDate(format: CMJsonField<String>) = formatDate(format.toDopeType())

@JvmName("strFormatCMStringType")
fun CMJsonField<String>.formatDate(format: TypeExpression<StringType>) = toDopeType().formatDate(format)

fun CMJsonField<String>.formatDate(format: String) = toDopeType().formatDate(format.toDopeType())

fun String.formatDate(format: CMJsonField<String>) = toDopeType().formatDate(format.toDopeType())
