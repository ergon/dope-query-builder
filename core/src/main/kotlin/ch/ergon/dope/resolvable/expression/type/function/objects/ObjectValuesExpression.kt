package ch.ergon.dope.resolvable.expression.type.function.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

data class ObjectValuesExpression(
    val objectExpression: TypeExpression<ObjectType>,
) : FunctionExpression<ArrayType<out ValidType>>("OBJECT_VALUES", listOf(objectExpression))

fun TypeExpression<ObjectType>.getValues() = ObjectValuesExpression(this)
