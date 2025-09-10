package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

data class ObjectUnwrapExpression(
    val objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ValidType>("OBJECT_UNWRAP", listOf(objectExpression))

fun TypeExpression<ObjectType>.unwrap() = ObjectUnwrapExpression(this)
