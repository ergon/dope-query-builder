package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsObjectExpression<T : ValidType>(val expression: TypeExpression<T>) :
    FunctionExpression<BooleanType>("ISOBJECT", listOf(expression))

fun <T : ValidType> TypeExpression<T>.isObject() = IsObjectExpression(this)
