package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.resolvable.expression.operator.PrefixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType

data class NotExpression(
    val expression: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, PrefixOperator(expression)

fun not(expression: TypeExpression<BooleanType>) = NotExpression(expression)

fun not(boolean: Boolean) = not(boolean.toDopeType())
