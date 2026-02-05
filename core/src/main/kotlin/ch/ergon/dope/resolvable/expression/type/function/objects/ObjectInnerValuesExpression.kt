package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

data class ObjectInnerValuesExpression(
    val objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<ValidType>>(listOf(objectExpression))

fun TypeExpression<ObjectType>.getInnerValues() = ObjectInnerValuesExpression(this)
