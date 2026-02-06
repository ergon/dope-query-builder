package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.position
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.position(searchStr: CMJsonField<String>) =
    toDopeType().position(searchStr.toDopeType())

fun CMJsonField<String>.position(searchStr: TypeExpression<StringType>) =
    toDopeType().position(searchStr)

fun CMJsonField<String>.position(searchStr: String) =
    toDopeType().position(searchStr.toDopeType())

fun String.position(searchStr: CMJsonField<String>) =
    toDopeType().position(searchStr.toDopeType())

fun TypeExpression<StringType>.position(searchStr: CMJsonField<String>) =
    position(searchStr.toDopeType())

fun String.position(searchStr: TypeExpression<StringType>) =
    toDopeType().position(searchStr)

fun String.position(searchStr: String) =
    toDopeType().position(searchStr.toDopeType())
