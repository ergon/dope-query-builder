package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType

data class ObjectConcatExpression(
    val firstObjectExpression: TypeExpression<ObjectType>,
    val secondObjectExpression: TypeExpression<ObjectType>,
    val additionalObjectExpression: List<TypeExpression<ObjectType>> = emptyList(),
) : FunctionExpression<ObjectType>(
    "OBJECT_CONCAT",
    listOf(firstObjectExpression, secondObjectExpression, *additionalObjectExpression.toTypedArray()),
)

fun TypeExpression<ObjectType>.concat(
    secondObjectExpression: TypeExpression<ObjectType>,
    vararg additionalObjectExpression: TypeExpression<ObjectType>,
) = ObjectConcatExpression(this, secondObjectExpression, additionalObjectExpression.toList())
