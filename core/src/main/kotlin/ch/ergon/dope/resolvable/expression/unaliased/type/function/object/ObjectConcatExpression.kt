package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType

class ObjectConcatExpression(
    firstObjectExpression: TypeExpression<ObjectType>,
    secondObjectExpression: TypeExpression<ObjectType>,
    vararg additionalObjectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ObjectType>("OBJECT_CONCAT", firstObjectExpression, secondObjectExpression, *additionalObjectExpression)

fun TypeExpression<ObjectType>.concat(
    secondObjectExpression: TypeExpression<ObjectType>,
    vararg additionalObjectExpression: TypeExpression<ObjectType>,
) = ObjectConcatExpression(this, secondObjectExpression, *additionalObjectExpression)
