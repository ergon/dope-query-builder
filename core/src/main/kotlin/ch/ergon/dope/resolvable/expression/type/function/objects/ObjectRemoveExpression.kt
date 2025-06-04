package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectRemoveExpression(
    objectExpression: TypeExpression<ObjectType>,
    attributeKey: TypeExpression<StringType>,
) : FunctionExpression<ObjectType>("OBJECT_REMOVE", objectExpression, attributeKey)

fun TypeExpression<ObjectType>.removeAttribute(key: TypeExpression<StringType>) = ObjectRemoveExpression(this, key)

fun TypeExpression<ObjectType>.removeAttribute(key: String) = removeAttribute(key.toDopeType())
