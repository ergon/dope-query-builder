package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectFieldExpression(
    objectExpression: TypeExpression<ObjectType>,
    attributeKey: TypeExpression<StringType>,
) : FunctionExpression<ValidType>("OBJECT_FIELD", objectExpression, attributeKey)

fun TypeExpression<ObjectType>.getField(key: TypeExpression<StringType>) =
    ObjectFieldExpression(this, key)

fun TypeExpression<ObjectType>.getField(key: String) = getField(key.toDopeType())
