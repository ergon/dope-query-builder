package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType

class OrExpression(
    left: TypeExpression<BooleanType>,
    right: TypeExpression<BooleanType>,
) : LogicalInfixExpression(left, "OR", right)

fun TypeExpression<BooleanType>.or(booleanExpression: TypeExpression<BooleanType>) = OrExpression(this, booleanExpression)

fun TypeExpression<BooleanType>.or(boolean: Boolean) = or(boolean.toDopeType())

fun Boolean.or(booleanExpression: TypeExpression<BooleanType>) = toDopeType().or(booleanExpression)
