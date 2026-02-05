package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType

sealed class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    right: TypeExpression<BooleanType>,
) : InfixOperator<BooleanType>(left, right)
