package ch.ergon.dope.resolvable.expression.single.type.logic

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.BooleanType

class AndExpression(
    left: TypeExpression<BooleanType>,
    right: TypeExpression<BooleanType>,
) : LogicalInfixExpression(left, "AND", right)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>) = AndExpression(this, booleanExpression)

fun TypeExpression<BooleanType>.and(boolean: Boolean) = and(boolean.toDopeType())

fun Boolean.and(booleanExpression: TypeExpression<BooleanType>) = toDopeType().and(booleanExpression)
