package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class PositionExpression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("POSITION", inStr, searchStr)

fun position(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    PositionExpression(inStr, searchStr)

fun position(inStr: TypeExpression<StringType>, searchStr: String) = PositionExpression(inStr, searchStr.toDopeType())

fun position(inStr: String, searchStr: TypeExpression<StringType>) = PositionExpression(inStr.toDopeType(), searchStr)

fun position(inStr: String, searchStr: String) = PositionExpression(inStr.toDopeType(), searchStr.toDopeType())
