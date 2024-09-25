package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsObjectExpression<T : ValidType>(expression: TypeExpression<T>) : TypeFunction<T, BooleanType>(expression, "ISOBJECT")

fun <T : ValidType> TypeExpression<T>.isObject() = IsObjectExpression(this)
