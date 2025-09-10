package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArraySymmetricDifferenceExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>("ARRAY_SYMDIFF", firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))

fun <T : ValidType> arraySymDiff(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceExpression(firstArray, secondArray, additionalArrays.toList())

fun <T : ValidType> arraySymDiff(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff(firstArray.asExpression(), secondArray, *additionalArrays)

fun <T : ValidType> arraySymDiff(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiff(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiff(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)

data class ArraySymmetricDifference1Expression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>("ARRAY_SYMDIFF1", firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))

fun <T : ValidType> arraySymDiff1(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifference1Expression(firstArray, secondArray, additionalArrays.toList())

fun <T : ValidType> arraySymDiff1(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff1(firstArray.asExpression(), secondArray, *additionalArrays)

fun <T : ValidType> arraySymDiff1(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff1(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiff1(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff1(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff1(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiff1(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiff1(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff1(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff1(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff1(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiff1(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiff1(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)

data class ArraySymmetricDifferenceNExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>("ARRAY_SYMDIFFN", firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))

fun <T : ValidType> arraySymDiffN(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceNExpression(firstArray, secondArray, additionalArrays.toList())

fun <T : ValidType> arraySymDiffN(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiffN(firstArray.asExpression(), secondArray, *additionalArrays)

fun <T : ValidType> arraySymDiffN(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiffN(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiffN(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiffN(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiffN(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arraySymDiffN(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arraySymDiffN(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiffN(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiffN(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiffN(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arraySymDiffN(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arraySymDiffN(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)
