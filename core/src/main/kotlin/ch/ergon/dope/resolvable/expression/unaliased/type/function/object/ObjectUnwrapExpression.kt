package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType

class ObjectUnwrapExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ObjectType>("OBJECT_UNWRAP", objectExpression)

fun TypeExpression<ObjectType>.unwrap() = ObjectUnwrapExpression(this)
