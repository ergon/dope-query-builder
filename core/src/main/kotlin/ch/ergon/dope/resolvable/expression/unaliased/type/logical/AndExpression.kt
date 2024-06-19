package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.validtype.BooleanType

class AndExpression(
    left: TypeExpression<BooleanType>,
    right: TypeExpression<BooleanType>,
) : LogicalInfixExpression(left, "AND", right)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    AndExpression(this, booleanExpression)

fun TypeExpression<BooleanType>.and(boolean: Boolean): LogicalInfixExpression =
    this.and(boolean.toBooleanType())

fun Boolean.and(booleanExpression: TypeExpression<BooleanType>): LogicalInfixExpression =
    this.toBooleanType().and(booleanExpression)
