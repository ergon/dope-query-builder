package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayAverageExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) : ArrayFunctionNumberExpression<T>("ARRAY_AVG", array)

fun <T : ValidType> arrayAverage(array: TypeExpression<ArrayType<T>>) = ArrayAverageExpression(array)
