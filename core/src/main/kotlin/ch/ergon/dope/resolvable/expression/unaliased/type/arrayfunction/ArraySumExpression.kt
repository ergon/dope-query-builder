package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArraySumExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionNumberExpression<T>("ARRAY_SUM", array)

fun <T : ValidType> arraySum(array: TypeExpression<ArrayType<T>>) = ArraySumExpression(array)
