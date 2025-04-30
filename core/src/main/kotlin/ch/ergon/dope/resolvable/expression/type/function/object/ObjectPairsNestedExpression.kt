package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

class ObjectPairsNestedExpression(
    objectExpression: TypeExpression<ObjectType>,
    options: TypeExpression<ObjectType>? = null,
) : FunctionExpression<ArrayType<ObjectType>>("OBJECT_PAIRS_NESTED", objectExpression, options)

fun TypeExpression<ObjectType>.pairsNested(options: TypeExpression<ObjectType>? = null) = ObjectPairsNestedExpression(this, options)
