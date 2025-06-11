package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType

class ObjectLengthExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<NumberType>("OBJECT_LENGTH", objectExpression)

fun TypeExpression<ObjectType>.getLength() = ObjectLengthExpression(this)
