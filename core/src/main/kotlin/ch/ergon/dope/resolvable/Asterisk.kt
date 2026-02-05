package ch.ergon.dope.resolvable

import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType

data class Asterisk(val path: SingleExpression<ObjectType>? = null) : Selectable, Returnable

fun asterisk(path: SingleExpression<ObjectType>? = null) = Asterisk(path)

@JvmName("asteriskSingleExpressionReceiver")
fun SingleExpression<ObjectType>.asterisk() = asterisk(this)

fun asterisk(obj: Map<String, Any>) = asterisk(obj.toDopeType())

@JvmName("asteriskObjectReceiver")
fun Map<String, Any>.asterisk() = asterisk(this)
