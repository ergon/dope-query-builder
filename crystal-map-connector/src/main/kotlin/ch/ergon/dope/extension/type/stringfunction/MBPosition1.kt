package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.mbPosition1
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
