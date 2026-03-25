package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbPosition1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbPosition1(searchStr: CMJsonField<String>) =
    toDopeType().mbPosition1(searchStr.toDopeType())

fun CMJsonField<String>.mbPosition1(searchStr: TypeExpression<StringType>) =
    toDopeType().mbPosition1(searchStr)

fun CMJsonField<String>.mbPosition1(searchStr: String) =
    toDopeType().mbPosition1(searchStr.toDopeType())

fun String.mbPosition1(searchStr: CMJsonField<String>) =
    toDopeType().mbPosition1(searchStr.toDopeType())

fun TypeExpression<StringType>.mbPosition1(searchStr: CMJsonField<String>) =
    mbPosition1(searchStr.toDopeType())
