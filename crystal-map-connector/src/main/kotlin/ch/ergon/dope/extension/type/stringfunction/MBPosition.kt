package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.mbPosition
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbPosition(inStr: CMJsonField<String>, searchStr: CMJsonField<String>) =
    mbPosition(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition(inStr: CMJsonField<String>, searchStr: TypeExpression<StringType>) =
    mbPosition(inStr.toDopeType(), searchStr)

fun mbPosition(inStr: CMJsonField<String>, searchStr: String) = mbPosition(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition(inStr: String, searchStr: CMJsonField<String>) = mbPosition(inStr.toDopeType(), searchStr.toDopeType())

fun mbPosition(inStr: TypeExpression<StringType>, searchStr: CMJsonField<String>) =
    mbPosition(inStr, searchStr.toDopeType())
