package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class ToObjectExpression<T : ValidType>(expression: TypeExpression<T>) : TypeFunction<T, ObjectType>(expression, "TOOBJECT")

fun <T : ValidType> TypeExpression<T>.toObject() = ToObjectExpression(this)

fun Number.toObject() = ToObjectExpression(this.toDopeType())

fun String.toObject() = ToObjectExpression(this.toDopeType())

fun Boolean.toObject() = ToObjectExpression(this.toDopeType())
