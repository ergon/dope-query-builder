package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class Position1Expression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("POSITION1", inStr, searchStr)

fun position1(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    Position1Expression(inStr, searchStr)

fun position1(inStr: TypeExpression<StringType>, searchStr: String) = position1(inStr, searchStr.toDopeType())

fun position1(inStr: String, searchStr: TypeExpression<StringType>) = position1(inStr.toDopeType(), searchStr)

fun position1(inStr: String, searchStr: String) = position1(inStr.toDopeType(), searchStr.toDopeType())
