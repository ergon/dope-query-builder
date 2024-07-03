package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayPutExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg values: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_PUT", array, value, *values)

fun <T : ValidType> arrayPut(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>, vararg values: TypeExpression<T>) =
    ArrayPutExpression(array, value, *values)
