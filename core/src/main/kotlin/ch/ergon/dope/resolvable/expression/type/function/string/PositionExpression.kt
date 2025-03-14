package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class PositionExpression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>("POSITION", inStr, searchStr)

fun position(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    PositionExpression(inStr, searchStr)

fun position(inStr: TypeExpression<StringType>, searchStr: String) = position(inStr, searchStr.toDopeType())

fun position(inStr: String, searchStr: TypeExpression<StringType>) = position(inStr.toDopeType(), searchStr)

fun position(inStr: String, searchStr: String) = position(inStr.toDopeType(), searchStr.toDopeType())
