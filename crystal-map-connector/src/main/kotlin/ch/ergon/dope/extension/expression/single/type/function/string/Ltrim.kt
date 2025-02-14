package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.ltrim
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun ltrim(inStr: CMJsonField<String>, extra: CMJsonField<String>) =
    ltrim(inStr.toDopeType(), extra.toDopeType())

fun ltrim(inStr: CMJsonField<String>, extra: String) =
    ltrim(inStr.toDopeType(), extra.toDopeType())

fun ltrim(inStr: String, extra: CMJsonField<String>) =
    ltrim(inStr.toDopeType(), extra.toDopeType())

fun ltrim(inStr: TypeExpression<StringType>, extra: CMJsonField<String>) =
    ltrim(inStr, extra.toDopeType())

fun ltrim(inStr: CMJsonField<String>, extra: TypeExpression<StringType>? = null) =
    ltrim(inStr.toDopeType(), extra)
