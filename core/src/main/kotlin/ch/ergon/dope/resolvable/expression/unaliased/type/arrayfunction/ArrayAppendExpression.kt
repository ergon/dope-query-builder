package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayAppendExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg values: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_APPEND", array, value, *values)

fun <T : ValidType> arrayAppend(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg values: TypeExpression<T>,
) = ArrayAppendExpression(array, value, *values)
