package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType

data class ObjectLengthExpression(
    val objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<NumberType>("OBJECT_LENGTH", listOf(objectExpression))

fun TypeExpression<ObjectType>.getLength() = ObjectLengthExpression(this)
