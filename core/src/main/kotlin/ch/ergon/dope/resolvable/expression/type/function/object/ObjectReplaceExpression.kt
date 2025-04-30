package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class ObjectReplaceExpression(
    objectExpression: TypeExpression<ObjectType>,
    oldValue: TypeExpression<out ValidType>,
    newValue: TypeExpression<out ValidType>,
) : FunctionExpression<ObjectType>("OBJECT_REPLACE", objectExpression, oldValue, newValue)

fun TypeExpression<ObjectType>.replace(oldValue: TypeExpression<out ValidType>, newValue: TypeExpression<out ValidType>) =
    ObjectReplaceExpression(this, oldValue, newValue)
