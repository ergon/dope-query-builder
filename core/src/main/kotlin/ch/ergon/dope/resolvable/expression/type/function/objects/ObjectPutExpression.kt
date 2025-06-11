package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectPutExpression(
    objectExpression: TypeExpression<ObjectType>,
    attributeKey: TypeExpression<StringType>,
    attributeValue: TypeExpression<out ValidType>,
) : FunctionExpression<ObjectType>("OBJECT_PUT", objectExpression, attributeKey, attributeValue)

fun TypeExpression<ObjectType>.putAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    ObjectPutExpression(this, key, value)

fun TypeExpression<ObjectType>.putAttribute(key: String, value: TypeExpression<out ValidType>) = putAttribute(key.toDopeType(), value)
