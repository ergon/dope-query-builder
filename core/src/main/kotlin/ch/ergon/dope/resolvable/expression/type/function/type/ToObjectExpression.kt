package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class ToObjectExpression<T : ValidType>(expression: TypeExpression<T>) : FunctionExpression<ObjectType>(
    "TOOBJECT",
    expression,
)

fun <T : ValidType> TypeExpression<T>.toObject() = ToObjectExpression(this)

fun Number.toObject() = ToObjectExpression(this.toDopeType())

fun String.toObject() = ToObjectExpression(this.toDopeType())

fun Boolean.toObject() = ToObjectExpression(this.toDopeType())
