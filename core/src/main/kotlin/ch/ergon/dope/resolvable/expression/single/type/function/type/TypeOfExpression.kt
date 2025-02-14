package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class TypeOfExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<
    StringType,>(
    "TYPE",
    expression,
)

fun <T : ValidType> typeOf(expression: TypeExpression<T>) = TypeOfExpression(expression)
