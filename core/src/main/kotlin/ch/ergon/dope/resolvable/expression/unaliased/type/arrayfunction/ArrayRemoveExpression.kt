package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayRemoveExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg values: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_REMOVE", array, value, *values)

fun <T : ValidType> arrayRemove(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg values: TypeExpression<T>,
) = ArrayRemoveExpression(array, value, *values)
