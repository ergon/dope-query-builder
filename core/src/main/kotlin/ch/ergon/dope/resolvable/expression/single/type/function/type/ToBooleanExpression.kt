package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class ToBooleanExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<
    BooleanType,>(
    "TOBOOLEAN",
    expression,
)

fun <T : ValidType> TypeExpression<T>.toBool() = ToBooleanExpression(this)

fun Number.toBool() = toDopeType().toBool()

fun String.toBool() = toDopeType().toBool()
