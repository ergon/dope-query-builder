package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.validtype.BooleanType

class OrExpression(
    left: TypeExpression<BooleanType>,
    right: TypeExpression<BooleanType>,
) : LogicalInfixExpression(left, "OR", right)

fun TypeExpression<BooleanType>.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    OrExpression(this, booleanExpression)

fun TypeExpression<BooleanType>.or(boolean: Boolean): LogicalInfixExpression =
    this.or(boolean.toBooleanType())

fun Boolean.or(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toBooleanType().or(booleanExpression)
