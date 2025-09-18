package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class Position1Expression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun position1(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    Position1Expression(inStr, searchStr)

fun position1(inStr: TypeExpression<StringType>, searchStr: String) = position1(inStr, searchStr.toDopeType())

fun position1(inStr: String, searchStr: TypeExpression<StringType>) = position1(inStr.toDopeType(), searchStr)

fun position1(inStr: String, searchStr: String) = position1(inStr.toDopeType(), searchStr.toDopeType())
