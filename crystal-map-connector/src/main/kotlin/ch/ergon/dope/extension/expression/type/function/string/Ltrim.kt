package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ltrim
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.ltrim(extra: CMJsonField<String>) =
    toDopeType().ltrim(extra.toDopeType())

fun CMJsonField<String>.ltrim(extra: String) =
    toDopeType().ltrim(extra.toDopeType())

fun String.ltrim(extra: CMJsonField<String>) =
    toDopeType().ltrim(extra.toDopeType())

fun TypeExpression<StringType>.ltrim(extra: CMJsonField<String>) =
    ltrim(extra.toDopeType())

fun CMJsonField<String>.ltrim(extra: TypeExpression<StringType>? = null) =
    toDopeType().ltrim(extra)
