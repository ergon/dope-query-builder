package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.contains
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun contains(inStr: CMJsonField<String>, searchStr: CMJsonField<String>) =
    contains(inStr.toDopeType(), searchStr.toDopeType())

fun contains(inStr: CMJsonField<String>, searchStr: TypeExpression<StringType>) =
    contains(inStr.toDopeType(), searchStr)

fun contains(inStr: TypeExpression<StringType>, searchStr: CMJsonField<String>) =
    contains(inStr, searchStr.toDopeType())

fun contains(inStr: CMJsonField<String>, searchStr: String) =
    contains(inStr.toDopeType(), searchStr.toDopeType())

fun contains(inStr: String, searchStr: CMJsonField<String>) =
    contains(inStr.toDopeType(), searchStr.toDopeType())
