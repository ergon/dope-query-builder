package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToStringExpression<T : ValidType>(expression: TypeExpression<T>) : TypeFunction<T, StringType>(expression, "TOSTRING")

fun <T : ValidType> TypeExpression<T>.toStr() = ToStringExpression(this)

fun Number.toStr() = ToStringExpression(toDopeType())

fun Boolean.toStr() = ToStringExpression(toDopeType())
