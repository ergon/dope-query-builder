package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

class ObjectInnerPairsExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<ObjectType>>("OBJECT_INNER_PAIRS", objectExpression)

fun TypeExpression<ObjectType>.innerPairs() = ObjectInnerPairsExpression(this)
