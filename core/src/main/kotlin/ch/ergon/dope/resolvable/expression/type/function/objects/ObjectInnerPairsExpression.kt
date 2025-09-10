package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

data class ObjectInnerPairsExpression(
    val objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<ObjectType>>("OBJECT_INNER_PAIRS", listOf(objectExpression))

fun TypeExpression<ObjectType>.getInnerPairs() = ObjectInnerPairsExpression(this)
