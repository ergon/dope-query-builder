package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayInsertExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val position: TypeExpression<NumberType>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : ArrayFunctionExpression<ArrayType<T>>(listOf(array, position, value) + additionalValues)

fun <T : ValidType> TypeExpression<ArrayType<T>>.insert(
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayInsertExpression(this, position, value, additionalValues.toList())

fun TypeExpression<ArrayType<StringType>>.insert(
    position: TypeExpression<NumberType>,
    value: String,
    vararg additionalValues: String,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.insert(
    position: TypeExpression<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.insert(
    position: TypeExpression<NumberType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> TypeExpression<ArrayType<T>>.insert(
    position: Number,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = insert(position.toDopeType(), value, *additionalValues)

fun TypeExpression<ArrayType<StringType>>.insert(
    position: Number,
    value: String,
    vararg additionalValues: String,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.insert(
    position: Number,
    value: Number,
    vararg additionalValues: Number,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.insert(
    position: Number,
    value: Boolean,
    vararg additionalValues: Boolean,
) = insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.insert(
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().insert(position, value, *additionalValues)

fun ISelectOffsetClause<StringType>.insert(
    position: TypeExpression<NumberType>,
    value: String,
    vararg additionalValues: String,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.insert(
    position: TypeExpression<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.insert(
    position: TypeExpression<NumberType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.insert(
    position: Number,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().insert(position, value, *additionalValues)

fun ISelectOffsetClause<StringType>.insert(
    position: Number,
    value: String,
    vararg additionalValues: String,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.insert(
    position: Number,
    value: Number,
    vararg additionalValues: Number,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.insert(
    position: Number,
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().insert(position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
