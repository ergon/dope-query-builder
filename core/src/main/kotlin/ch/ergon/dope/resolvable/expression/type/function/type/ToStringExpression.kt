package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToStringExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<
    StringType,>(
    "TOSTRING",
    expression,
)

fun <T : ValidType> TypeExpression<T>.toStr() = ToStringExpression(this)

fun Number.toStr() = ToStringExpression(toDopeType())

fun Boolean.toStr() = ToStringExpression(toDopeType())
