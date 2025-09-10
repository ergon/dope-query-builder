package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

data class ObjectPairsNestedExpression(
    val objectExpression: TypeExpression<ObjectType>,
    val options: TypeExpression<ObjectType>? = null,
) : FunctionExpression<ArrayType<ObjectType>>("OBJECT_PAIRS_NESTED", listOf(objectExpression, options))

fun TypeExpression<ObjectType>.getNestedPairs(options: TypeExpression<ObjectType>? = null) = ObjectPairsNestedExpression(this, options)
