package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectFieldExpression(
    objectExpression: TypeExpression<ObjectType>,
    attributeKey: TypeExpression<StringType>,
) : FunctionExpression<ValidType>("OBJECT_FIELD", objectExpression, attributeKey)

fun TypeExpression<ObjectType>.objectField(key: TypeExpression<StringType>) =
    ObjectFieldExpression(this, key)

fun TypeExpression<ObjectType>.objectField(key: String) = objectField(key.toDopeType())
