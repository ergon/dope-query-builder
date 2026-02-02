package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class ReverseExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(inStr))

fun TypeExpression<StringType>.reverse() = ReverseExpression(this)

fun String.reverse() = toDopeType().reverse()
