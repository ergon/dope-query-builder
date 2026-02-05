package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBPosition1Expression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun TypeExpression<StringType>.mbPosition1(searchStr: TypeExpression<StringType>) =
    MBPosition1Expression(this, searchStr)

fun TypeExpression<StringType>.mbPosition1(searchStr: String) = mbPosition1(searchStr.toDopeType())

fun String.mbPosition1(searchStr: TypeExpression<StringType>) = toDopeType().mbPosition1(searchStr)

fun String.mbPosition1(searchStr: String) = toDopeType().mbPosition1(searchStr.toDopeType())
