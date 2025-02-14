package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayIntersectExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_INTERSECT", firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arrayIntersect(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayIntersectExpression(firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arrayIntersect(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayIntersect(firstArray.asExpression(), secondArray, *additionalArrays)

fun <T : ValidType> arrayIntersect(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayIntersect(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayIntersect(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayIntersect(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayIntersect(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayIntersect(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayIntersect(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayIntersect(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayIntersect(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayIntersect(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayIntersect(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayIntersect(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)
