package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

sealed class ArrayFunctionExpression<T : ValidType>(
    val arguments: List<TypeExpression<out ValidType>>,
) : FunctionOperator<T>
