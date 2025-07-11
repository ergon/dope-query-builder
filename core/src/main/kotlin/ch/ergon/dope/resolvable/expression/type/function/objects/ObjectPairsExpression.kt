package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

class ObjectPairsExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<ObjectType>>("OBJECT_PAIRS", objectExpression)

fun TypeExpression<ObjectType>.getPairs() = ObjectPairsExpression(this)
