package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayIntersectExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_INTERSECT", firstArray, secondArray, *arrays)

fun <T : ValidType> arrayIntersect(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) = ArrayIntersectExpression(firstArray, secondArray, *arrays)
