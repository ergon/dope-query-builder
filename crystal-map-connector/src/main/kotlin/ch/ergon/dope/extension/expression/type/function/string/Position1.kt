package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.position1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.position1(searchStr: CMJsonField<String>) =
    toDopeType().position1(searchStr.toDopeType())

fun CMJsonField<String>.position1(searchStr: TypeExpression<StringType>) =
    toDopeType().position1(searchStr)

fun CMJsonField<String>.position1(searchStr: String) =
    toDopeType().position1(searchStr.toDopeType())

fun String.position1(searchStr: CMJsonField<String>) =
    toDopeType().position1(searchStr.toDopeType())

fun TypeExpression<StringType>.position1(searchStr: CMJsonField<String>) =
    position1(searchStr.toDopeType())

fun String.position1(searchStr: TypeExpression<StringType>) =
    toDopeType().position1(searchStr)

fun String.position1(searchStr: String) =
    toDopeType().position1(searchStr.toDopeType())
