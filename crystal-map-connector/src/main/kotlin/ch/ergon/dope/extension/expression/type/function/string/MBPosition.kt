package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbPosition
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbPosition(searchStr: CMJsonField<String>) =
    toDopeType().mbPosition(searchStr.toDopeType())

fun CMJsonField<String>.mbPosition(searchStr: TypeExpression<StringType>) =
    toDopeType().mbPosition(searchStr)

fun CMJsonField<String>.mbPosition(searchStr: String) =
    toDopeType().mbPosition(searchStr.toDopeType())

fun String.mbPosition(searchStr: CMJsonField<String>) =
    toDopeType().mbPosition(searchStr.toDopeType())

fun TypeExpression<StringType>.mbPosition(searchStr: CMJsonField<String>) =
    mbPosition(searchStr.toDopeType())
