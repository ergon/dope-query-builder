package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayConcatExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_CONCAT", firstArray, secondArray, *arrays)

fun <T : ValidType> arrayConcat(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg arrays: TypeExpression<ArrayType<T>>,
) = ArrayConcatExpression(firstArray, secondArray, *arrays)
