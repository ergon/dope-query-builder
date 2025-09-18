package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType

data class AndExpression(
    override val left: TypeExpression<BooleanType>,
    override val right: TypeExpression<BooleanType>,
) : LogicalInfixExpression(left, right)

fun TypeExpression<BooleanType>.and(booleanExpression: TypeExpression<BooleanType>) = AndExpression(this, booleanExpression)

fun TypeExpression<BooleanType>.and(boolean: Boolean) = and(boolean.toDopeType())

fun Boolean.and(booleanExpression: TypeExpression<BooleanType>) = toDopeType().and(booleanExpression)
