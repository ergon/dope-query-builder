package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayConcatExpression<T : ValidType>(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>("ARRAY_CONCAT", firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arrayConcat(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayConcatExpression(firstArray, secondArray, *additionalArrays)

fun <T : ValidType> arrayConcat(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayConcat(firstArray.asExpression(), secondArray, *additionalArrays)

fun <T : ValidType> arrayConcat(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayConcat(firstArray, secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayConcat(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayConcat(firstArray, secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayConcat(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = arrayConcat(firstArray.asExpression(), secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> arrayConcat(
    firstArray: ISelectOffsetClause<T>,
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayConcat(firstArray.asExpression(), secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayConcat(
    firstArray: TypeExpression<ArrayType<T>>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayConcat(firstArray, secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> arrayConcat(
    firstArray: ISelectOffsetClause<T>,
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = arrayConcat(
    firstArray.asExpression(),
    secondArray.asExpression(),
    *additionalArrays.map { it.asExpression() }.toTypedArray(),
)
