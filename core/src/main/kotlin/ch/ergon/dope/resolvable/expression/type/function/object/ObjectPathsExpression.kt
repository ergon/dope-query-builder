package ch.ergon.dope.resolvable.expression.type.function.`object`

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

class ObjectPathsExpression(
    objectExpression: TypeExpression<ObjectType>,
    options: TypeExpression<ObjectType>? = null,
) : FunctionExpression<ArrayType<StringType>>("OBJECT_PATHS", objectExpression, options)

fun TypeExpression<ObjectType>.getPaths(options: TypeExpression<ObjectType>? = null) = ObjectPathsExpression(this, options)
