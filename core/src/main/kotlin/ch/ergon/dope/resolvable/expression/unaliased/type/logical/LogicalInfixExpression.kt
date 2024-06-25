package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType

class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(): DopeQuery = toInfixDopeQuery(useBrackets = true)
}

fun TypeExpression<BooleanType>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    LogicalInfixExpression(this, "OR", booleanExpression)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    LogicalInfixExpression(this, "AND", booleanExpression)

fun TypeExpression<BooleanType>.or(boolean: Boolean): LogicalInfixExpression =
    this.or(boolean.toDopeType())

fun Boolean.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toDopeType().or(booleanExpression)

fun TypeExpression<BooleanType>.and(boolean: Boolean): LogicalInfixExpression =
    this.and(boolean.toDopeType())

fun Boolean.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toDopeType().and(booleanExpression)
