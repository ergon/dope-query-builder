package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectRemoveExpression(
    objectExpression: TypeExpression<ObjectType>,
    attributeKey: TypeExpression<StringType>,
) : FunctionExpression<ObjectType>("OBJECT_REMOVE", objectExpression, attributeKey)

fun TypeExpression<ObjectType>.removeAttribute(key: TypeExpression<StringType>) = ObjectRemoveExpression(this, key)

fun TypeExpression<ObjectType>.removeAttribute(key: String) = removeAttribute(key.toDopeType())
