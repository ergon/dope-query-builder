package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayUnionExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(firstArray, secondArray) + additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.union(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayUnionExpression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.union(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().union(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.union(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = union(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.union(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = union(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.union(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().union(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.union(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().union(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.union(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = union(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.union(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().union(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())
