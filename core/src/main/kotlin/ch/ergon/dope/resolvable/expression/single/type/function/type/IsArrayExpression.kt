package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsArrayExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<BooleanType>(
    "ISARRAY",
    expression,
)

fun <T : ValidType> TypeExpression<T>.isArray() = IsArrayExpression(this)
