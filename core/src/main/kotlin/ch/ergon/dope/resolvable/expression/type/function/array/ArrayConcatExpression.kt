package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayConcatExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>(firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))

fun <T : ValidType> TypeExpression<ArrayType<T>>.concat(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayConcatExpression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.concat(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().concat(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.concat(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = concat(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.concat(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = concat(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.concat(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().concat(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.concat(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().concat(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.concat(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = concat(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.concat(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().concat(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())
