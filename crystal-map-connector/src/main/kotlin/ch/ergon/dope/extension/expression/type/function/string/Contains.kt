package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.contains
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.contains(searchStr: CMJsonField<String>) =
    toDopeType().contains(searchStr.toDopeType())

fun CMJsonField<String>.contains(searchStr: TypeExpression<StringType>) =
    toDopeType().contains(searchStr)

fun CMJsonField<String>.contains(searchStr: String) =
    toDopeType().contains(searchStr.toDopeType())

fun TypeExpression<StringType>.contains(searchStr: CMJsonField<String>) =
    contains(searchStr.toDopeType())

fun String.contains(searchStr: TypeExpression<StringType>) =
    toDopeType().contains(searchStr)

fun String.contains(searchStr: CMJsonField<String>) =
    toDopeType().contains(searchStr.toDopeType())
