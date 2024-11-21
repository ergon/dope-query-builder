package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

class ObjectInnerValuesExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<ValidType>>("OBJECT_INNER_VALUES", objectExpression)

fun TypeExpression<ObjectType>.innerValues() = ObjectInnerValuesExpression(this)
