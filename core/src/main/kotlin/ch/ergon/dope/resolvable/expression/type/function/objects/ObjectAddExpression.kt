package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.expression.type.toObjectEntry
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ObjectAddExpression(
    val objectExpression: TypeExpression<ObjectType>,
    val objectEntryPrimitive: ObjectEntryPrimitive<out ValidType>,
) : FunctionExpression<ObjectType>(
    listOf(objectExpression, objectEntryPrimitive.key, objectEntryPrimitive.value),
)

fun TypeExpression<ObjectType>.addAttribute(objectEntryPrimitive: ObjectEntryPrimitive<out ValidType>) =
    ObjectAddExpression(this, objectEntryPrimitive)

fun TypeExpression<ObjectType>.addAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    addAttribute(key.toObjectEntry(value))

fun TypeExpression<ObjectType>.addAttribute(key: String, value: TypeExpression<out ValidType>) =
    addAttribute(key.toDopeType(), value)
