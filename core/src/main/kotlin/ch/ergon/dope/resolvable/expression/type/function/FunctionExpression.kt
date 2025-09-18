package ch.ergon.dope.resolvable.expression.type.function

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

abstract class FunctionExpression<T : ValidType>(
    val expressions: List<TypeExpression<out ValidType>?> = emptyList(),
) : TypeExpression<T>, FunctionOperator
