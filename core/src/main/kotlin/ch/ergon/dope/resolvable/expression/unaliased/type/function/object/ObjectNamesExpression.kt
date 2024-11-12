package ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectNamesExpression(
    objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<StringType>>("OBJECT_NAMES", objectExpression)

fun TypeExpression<ObjectType>.names() = ObjectNamesExpression(this)
