package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectAddExpression(
    objectExpression: TypeExpression<ObjectType>,
    newAttributeKey: TypeExpression<StringType>,
    newAttributeValue: TypeExpression<out ValidType>,
) : FunctionExpression<ObjectType>("OBJECT_ADD", objectExpression, newAttributeKey, newAttributeValue)

fun TypeExpression<ObjectType>.addAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    ObjectAddExpression(this, key, value)

fun TypeExpression<ObjectType>.addAttribute(key: String, value: TypeExpression<out ValidType>) = addAttribute(key.toDopeType(), value)
