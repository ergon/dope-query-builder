package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType

class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toQueryString(): String = toInfixQueryString()
}

fun TypeExpression<BooleanType>.or(booleanExpression: TypeExpression<BooleanType>) =
    LogicalInfixExpression(this, "OR", booleanExpression)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>) =
    LogicalInfixExpression(this, "AND", booleanExpression)
