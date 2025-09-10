package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType

sealed class NumberInfixExpression(
    left: TypeExpression<NumberType>,
    symbol: String,
    right: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, InfixOperator(left, symbol, right)
