package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBPosition1Expression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    MBPosition1Expression(inStr, searchStr)

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: String) =
    mbPosition1(inStr, searchStr.toDopeType())

fun mbPosition1(inStr: String, searchStr: TypeExpression<StringType>) =
    mbPosition1(inStr.toDopeType(), searchStr)

fun mbPosition1(inStr: String, searchStr: String) =
    mbPosition1(inStr.toDopeType(), searchStr.toDopeType())
