package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class PositionExpression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun TypeExpression<StringType>.position(searchStr: TypeExpression<StringType>) =
    PositionExpression(this, searchStr)

fun TypeExpression<StringType>.position(searchStr: String) = position(searchStr.toDopeType())

fun String.position(searchStr: TypeExpression<StringType>) = toDopeType().position(searchStr)

fun String.position(searchStr: String) = toDopeType().position(searchStr.toDopeType())
