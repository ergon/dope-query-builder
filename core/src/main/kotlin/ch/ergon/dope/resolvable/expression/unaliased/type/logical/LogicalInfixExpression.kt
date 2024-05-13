package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType

class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toQueryString(): String = toInfixQueryString(useBrackets = true)
}

fun TypeExpression<BooleanType>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    LogicalInfixExpression(this, "OR", booleanExpression)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    LogicalInfixExpression(this, "AND", booleanExpression)

fun TypeExpression<BooleanType>.or(boolean: Boolean): LogicalInfixExpression =
    this.or(boolean.toBooleanType())

fun Boolean.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toBooleanType().or(booleanExpression)

fun TypeExpression<BooleanType>.and(boolean: Boolean): LogicalInfixExpression =
    this.and(boolean.toBooleanType())

fun Boolean.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toBooleanType().and(booleanExpression)
