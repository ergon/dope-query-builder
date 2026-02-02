package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.split
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.split(inSubstring: CMJsonField<String>) =
    toDopeType().split(inSubstring.toDopeType())

fun CMJsonField<String>.split(inSubstring: String) =
    toDopeType().split(inSubstring.toDopeType())

fun String.split(inSubstring: CMJsonField<String>) =
    toDopeType().split(inSubstring.toDopeType())

fun TypeExpression<StringType>.split(inSubstring: CMJsonField<String>) =
    split(inSubstring.toDopeType())

fun CMJsonField<String>.split(inSubstring: TypeExpression<StringType>? = null) =
    toDopeType().split(inSubstring)

fun String.split(inSubstring: TypeExpression<StringType>? = null) =
    toDopeType().split(inSubstring)

fun String.split(inSubstring: String) =
    toDopeType().split(inSubstring.toDopeType())
