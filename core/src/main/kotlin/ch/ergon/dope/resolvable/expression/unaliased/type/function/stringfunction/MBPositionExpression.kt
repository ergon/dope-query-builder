package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class MBPositionExpression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("MB_POSITION", inStr, searchStr)

fun mbPosition(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    MBPositionExpression(inStr, searchStr)

fun mbPosition(inStr: TypeExpression<StringType>, searchStr: String) = MBPositionExpression(inStr, searchStr.toDopeType())

fun mbPosition(inStr: String, searchStr: TypeExpression<StringType>) = MBPositionExpression(inStr.toDopeType(), searchStr)

fun mbPosition(inStr: String, searchStr: String) = MBPositionExpression(inStr.toDopeType(), searchStr.toDopeType())
