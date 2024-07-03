package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayUnionExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_UNION", firstArray, secondArray, *arrays)

fun <T : ValidType> arrayUnion(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) = ArrayUnionExpression(firstArray, secondArray, *arrays)
