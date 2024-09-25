package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsStringExpression<T : ValidType>(expression: TypeExpression<T>) : TypeFunction<T, BooleanType>(expression, "ISSTRING")

fun <T : ValidType> TypeExpression<T>.isString() = IsStringExpression(this)
