package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

data class ObjectReplaceExpression(
    val objectExpression: TypeExpression<ObjectType>,
    val oldValue: TypeExpression<out ValidType>,
    val newValue: TypeExpression<out ValidType>,
) : FunctionExpression<ObjectType>("OBJECT_REPLACE", listOf(objectExpression, oldValue, newValue))

fun TypeExpression<ObjectType>.replace(oldValue: TypeExpression<out ValidType>, newValue: TypeExpression<out ValidType>) =
    ObjectReplaceExpression(this, oldValue, newValue)
