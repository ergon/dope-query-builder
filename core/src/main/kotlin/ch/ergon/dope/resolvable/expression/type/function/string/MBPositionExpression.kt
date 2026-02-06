package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBPositionExpression(val inStr: TypeExpression<StringType>, val searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr, searchStr))

fun TypeExpression<StringType>.mbPosition(searchStr: TypeExpression<StringType>) =
    MBPositionExpression(this, searchStr)

fun TypeExpression<StringType>.mbPosition(searchStr: String) = mbPosition(searchStr.toDopeType())

fun String.mbPosition(searchStr: TypeExpression<StringType>) = toDopeType().mbPosition(searchStr)

fun String.mbPosition(searchStr: String) = toDopeType().mbPosition(searchStr.toDopeType())
