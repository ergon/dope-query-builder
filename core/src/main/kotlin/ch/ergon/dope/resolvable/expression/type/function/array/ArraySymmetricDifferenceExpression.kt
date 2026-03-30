package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArraySymmetricDifferenceExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(firstArray, secondArray) + additionalArrays)

data class ArraySymmetricDifference1Expression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(firstArray, secondArray) + additionalArrays)

data class ArraySymmetricDifferenceNExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(firstArray, secondArray) + additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceExpression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiff(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = symDiff(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiff(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiff(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.symDiff(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiff(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiff(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiff(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff1(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifference1Expression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff1(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiff1(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff1(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = symDiff1(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff1(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiff1(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff1(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiff1(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.symDiff1(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiff1(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiff1(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiff1(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiff1(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiff1(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiffN(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArraySymmetricDifferenceNExpression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.symDiffN(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiffN(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiffN(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = symDiffN(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiffN(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiffN(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiffN(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().symDiffN(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.symDiffN(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiffN(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.symDiffN(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = symDiffN(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.symDiffN(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().symDiffN(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())
