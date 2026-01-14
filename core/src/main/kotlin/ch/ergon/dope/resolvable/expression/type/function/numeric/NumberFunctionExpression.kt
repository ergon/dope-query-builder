package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType

sealed class NumberFunctionExpression(
    open val value: TypeExpression<NumberType>? = null,
    val additionalValue: TypeExpression<NumberType>? = null,
) : FunctionOperator<NumberType>
