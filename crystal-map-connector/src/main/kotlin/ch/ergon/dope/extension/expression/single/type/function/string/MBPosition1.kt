package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.mbPosition1
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbPosition1(inStr: CMJsonField<String>, searchStr: CMJsonField<String>) =
    mbPosition1(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition1(inStr: CMJsonField<String>, searchStr: TypeExpression<StringType>) =
    mbPosition1(inStr.toDopeType(), searchStr)

fun mbPosition1(inStr: CMJsonField<String>, searchStr: String) = mbPosition1(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition1(inStr: String, searchStr: CMJsonField<String>) = mbPosition1(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: CMJsonField<String>) =
    mbPosition1(inStr, searchStr.toDopeType())
