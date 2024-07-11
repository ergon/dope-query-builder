package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayMaxExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionNumberExpression<T>("ARRAY_MAX", array)

fun <T : ValidType> arrayMax(array: TypeExpression<ArrayType<T>>) = ArrayMaxExpression(array)
