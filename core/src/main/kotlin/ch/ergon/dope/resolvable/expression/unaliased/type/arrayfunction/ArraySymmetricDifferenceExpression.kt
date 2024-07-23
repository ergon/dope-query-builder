package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArraySymmetricDifferenceExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_SYMDIFF", firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arraySymDiff(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceExpression(firstArray, secondArray, *additionalArrays)

// only an alias (defined by couchbase)
fun <T : ValidType> arraySymDiff1(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff(firstArray, secondArray, *additionalArrays)

class ArraySymmetricDifferenceNExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_SYMDIFFN", firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arraySymDiffN(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceNExpression(firstArray, secondArray, *additionalArrays)
