package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

data class ObjectPathsExpression(
    val objectExpression: TypeExpression<ObjectType>,
    val options: TypeExpression<ObjectType>? = null,
) : FunctionExpression<ArrayType<StringType>>(listOf(objectExpression, options))

fun TypeExpression<ObjectType>.getPaths(options: TypeExpression<ObjectType>? = null) = ObjectPathsExpression(this, options)
