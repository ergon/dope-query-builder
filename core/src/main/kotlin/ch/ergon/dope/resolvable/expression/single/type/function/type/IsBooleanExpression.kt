package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsBooleanExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<
    BooleanType,>(
    "ISBOOLEAN",
    expression,
)

fun <T : ValidType> TypeExpression<T>.isBoolean() = IsBooleanExpression(this)
