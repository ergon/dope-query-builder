package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.trim
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.trim(char: TypeExpression<StringType>? = null) =
    toDopeType().trim(char)

fun TypeExpression<StringType>.trim(char: CMJsonField<String>) =
    trim(char.toDopeType())

fun CMJsonField<String>.trim(char: CMJsonField<String>) =
    toDopeType().trim(char.toDopeType())

fun CMJsonField<String>.trim(char: String) =
    toDopeType().trim(char.toDopeType())

fun CMJsonField<String>.trim(char: Char) =
    toDopeType().trim(char.toString().toDopeType())

fun String.trim(char: CMJsonField<String>) =
    toDopeType().trim(char.toDopeType())

fun String.trim(char: TypeExpression<StringType>? = null) =
    toDopeType().trim(char)

fun String.trim(char: String) =
    toDopeType().trim(char.toDopeType())
