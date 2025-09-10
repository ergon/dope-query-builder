package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayUnionExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>("ARRAY_UNION", firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))

fun <T : ValidType> arrayUnion(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayUnionExpression(firstArray, secondArray, additionalArrays.toList())

fun <T : ValidType> arrayUnion(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayUnionExpression(firstArray.asExpression(), secondArray, additionalArrays.toList())

fun <T : ValidType> arrayUnion(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayUnion(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayUnion(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayUnion(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayUnion(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayUnion(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayUnion(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayUnion(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayUnion(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayUnion(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayUnion(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayUnion(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)
