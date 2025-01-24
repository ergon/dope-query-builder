package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.position1
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun position1(inStr: CMJsonField<String>, searchStr: CMJsonField<String>) =
    position1(inStr.toDopeType(), searchStr.toDopeType())

fun position1(inStr: CMJsonField<String>, searchStr: TypeExpression<StringType>) =
    position1(inStr.toDopeType(), searchStr)

fun position1(inStr: CMJsonField<String>, searchStr: String) = position1(inStr.toDopeType(), searchStr.toDopeType())

fun position1(inStr: String, searchStr: CMJsonField<String>) = position1(inStr.toDopeType(), searchStr.toDopeType())

fun position1(inStr: TypeExpression<StringType>, searchStr: CMJsonField<String>) =
    position1(inStr, searchStr.toDopeType())
