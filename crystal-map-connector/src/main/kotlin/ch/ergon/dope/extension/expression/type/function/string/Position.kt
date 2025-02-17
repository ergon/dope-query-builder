package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.position
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun position(inStr: CMJsonField<String>, searchStr: CMJsonField<String>) =
    position(inStr.toDopeType(), searchStr.toDopeType())

fun position(inStr: CMJsonField<String>, searchStr: TypeExpression<StringType>) =
    position(inStr.toDopeType(), searchStr)

fun position(inStr: CMJsonField<String>, searchStr: String) = position(inStr.toDopeType(), searchStr.toDopeType())

fun position(inStr: String, searchStr: CMJsonField<String>) = position(inStr.toDopeType(), searchStr.toDopeType())

fun position(inStr: TypeExpression<StringType>, searchStr: CMJsonField<String>) =
    position(inStr, searchStr.toDopeType())
