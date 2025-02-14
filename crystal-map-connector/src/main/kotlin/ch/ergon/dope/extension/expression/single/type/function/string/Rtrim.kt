package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun rtrim(inStr: CMJsonField<String>, extra: CMJsonField<String>) =
    rtrim(inStr.toDopeType(), extra.toDopeType())

fun rtrim(inStr: CMJsonField<String>, extra: String) =
    rtrim(inStr.toDopeType(), extra.toDopeType())

fun rtrim(inStr: String, extra: CMJsonField<String>) =
    rtrim(inStr.toDopeType(), extra.toDopeType())

fun rtrim(inStr: TypeExpression<StringType>, extra: CMJsonField<String>) =
    rtrim(inStr, extra.toDopeType())

fun rtrim(inStr: CMJsonField<String>, extra: TypeExpression<StringType>? = null) =
    rtrim(inStr.toDopeType(), extra)
