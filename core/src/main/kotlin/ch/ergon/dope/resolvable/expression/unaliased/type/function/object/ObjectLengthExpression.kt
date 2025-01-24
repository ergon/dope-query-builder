package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType

class ObjectLengthExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<NumberType>("OBJECT_LENGTH", objectExpression)

fun TypeExpression<ObjectType>.length() = ObjectLengthExpression(this)
