package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsNumberExpression<T : ValidType>(val expression: TypeExpression<T>) :
    FunctionExpression<BooleanType>("ISNUMBER", listOf(expression))

fun <T : ValidType> TypeExpression<T>.isNumber() = IsNumberExpression(this)
