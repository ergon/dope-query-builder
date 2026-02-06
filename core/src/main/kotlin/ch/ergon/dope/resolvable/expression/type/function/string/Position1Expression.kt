package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class Position1Expression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun TypeExpression<StringType>.position1(searchStr: TypeExpression<StringType>) =
    Position1Expression(this, searchStr)

fun TypeExpression<StringType>.position1(searchStr: String) = position1(searchStr.toDopeType())

fun String.position1(searchStr: TypeExpression<StringType>) = toDopeType().position1(searchStr)

fun String.position1(searchStr: String) = toDopeType().position1(searchStr.toDopeType())
