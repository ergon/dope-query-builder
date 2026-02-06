package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.rtrim(extra: CMJsonField<String>) =
    toDopeType().rtrim(extra.toDopeType())

fun CMJsonField<String>.rtrim(extra: String) =
    toDopeType().rtrim(extra.toDopeType())

fun String.rtrim(extra: CMJsonField<String>) =
    toDopeType().rtrim(extra.toDopeType())

fun TypeExpression<StringType>.rtrim(extra: CMJsonField<String>) =
    rtrim(extra.toDopeType())

fun CMJsonField<String>.rtrim(extra: TypeExpression<StringType>? = null) =
    toDopeType().rtrim(extra)

fun String.rtrim(extra: TypeExpression<StringType>? = null) =
    toDopeType().rtrim(extra)

fun String.rtrim(extra: String) =
    toDopeType().rtrim(extra.toDopeType())
