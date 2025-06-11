package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.expression.type.toObjectEntry
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectAddExpression(
    objectExpression: TypeExpression<ObjectType>,
    objectEntryPrimitive: ObjectEntryPrimitive<out ValidType>,
) : FunctionExpression<ObjectType>("OBJECT_ADD", objectExpression, objectEntryPrimitive.key, objectEntryPrimitive.value)

fun TypeExpression<ObjectType>.addAttribute(objectEntryPrimitive: ObjectEntryPrimitive<out ValidType>) =
    ObjectAddExpression(this, objectEntryPrimitive)

fun TypeExpression<ObjectType>.addAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    addAttribute(key.toObjectEntry(value))

fun TypeExpression<ObjectType>.addAttribute(key: String, value: TypeExpression<out ValidType>) =
    addAttribute(key.toDopeType(), value)
