package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayRemoveExpression<T : ValidType>(
    override val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : ArrayFunctionExpression<T>(array, listOf(value, *additionalValues.toTypedArray()))

fun <T : ValidType> arrayRemove(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayRemoveExpression(array, value, additionalValues.toList())

fun arrayRemove(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayRemove(
    selectClause: ISelectOffsetClause<T>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayRemove(selectClause.asExpression(), value, *additionalValues)

fun arrayRemove(
    selectClause: ISelectOffsetClause<StringType>,
    value: String,
    vararg additionalValues: String,
) = arrayRemove(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    selectClause: ISelectOffsetClause<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = arrayRemove(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    selectClause: ISelectOffsetClause<BooleanType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayRemove(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
