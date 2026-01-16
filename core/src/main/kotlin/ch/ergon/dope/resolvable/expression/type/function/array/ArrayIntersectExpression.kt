package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayIntersectExpression<T : ValidType>(
    val firstArray: TypeExpression<ArrayType<T>>,
    val secondArray: TypeExpression<ArrayType<T>>,
    val additionalArrays: List<TypeExpression<ArrayType<T>>> = emptyList(),
) : ArrayFunctionExpression<T>(firstArray, listOf(secondArray, *additionalArrays.toTypedArray()))
fun <T : ValidType> TypeExpression<ArrayType<T>>.intersect(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = ArrayIntersectExpression(this, secondArray, additionalArrays.toList())

fun <T : ValidType> ISelectOffsetClause<T>.intersect(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().intersect(secondArray, *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.intersect(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = intersect(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> TypeExpression<ArrayType<T>>.intersect(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = intersect(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.intersect(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: TypeExpression<ArrayType<T>>,
) = asExpression().intersect(secondArray.asExpression(), *additionalArrays)

fun <T : ValidType> ISelectOffsetClause<T>.intersect(
    secondArray: TypeExpression<ArrayType<T>>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().intersect(secondArray, *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.intersect(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = intersect(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.intersect(
    secondArray: ISelectOffsetClause<T>,
    vararg additionalArrays: ISelectOffsetClause<T> = emptyArray(),
) = asExpression().intersect(secondArray.asExpression(), *additionalArrays.map { it.asExpression() }.toTypedArray())
