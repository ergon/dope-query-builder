package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayInsertExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_INSERT", array, position, value, *additionalValues)

fun <T : ValidType> arrayInsert(
    array: TypeExpression<ArrayType<T>>,
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayInsertExpression(array, position, value, *additionalValues)

fun arrayInsert(
    array: TypeExpression<ArrayType<StringType>>,
    position: TypeExpression<NumberType>,
    value: String,
    vararg additionalValues: String,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    array: TypeExpression<ArrayType<NumberType>>,
    position: TypeExpression<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    array: TypeExpression<ArrayType<BooleanType>>,
    position: TypeExpression<NumberType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayInsert(
    array: TypeExpression<ArrayType<T>>,
    position: Number,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayInsert(array, position.toDopeType(), value, *additionalValues)

fun arrayInsert(
    array: TypeExpression<ArrayType<StringType>>,
    position: Number,
    value: String,
    vararg additionalValues: String,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    array: TypeExpression<ArrayType<NumberType>>,
    position: Number,
    value: Number,
    vararg additionalValues: Number,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    array: TypeExpression<ArrayType<BooleanType>>,
    position: Number,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayInsert(array, position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayInsert(
    selectClause: ISelectOffsetClause<T>,
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayInsertExpression(selectClause.asExpression(), position, value, *additionalValues)

fun arrayInsert(
    selectClause: ISelectOffsetClause<StringType>,
    position: TypeExpression<NumberType>,
    value: String,
    vararg additionalValues: String,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    selectClause: ISelectOffsetClause<NumberType>,
    position: TypeExpression<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    selectClause: ISelectOffsetClause<BooleanType>,
    position: TypeExpression<NumberType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayInsert(
    selectClause: ISelectOffsetClause<T>,
    position: Number,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayInsert(selectClause.asExpression(), position.toDopeType(), value, *additionalValues)

fun arrayInsert(
    selectClause: ISelectOffsetClause<StringType>,
    position: Number,
    value: String,
    vararg additionalValues: String,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    selectClause: ISelectOffsetClause<NumberType>,
    position: Number,
    value: Number,
    vararg additionalValues: Number,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayInsert(
    selectClause: ISelectOffsetClause<BooleanType>,
    position: Number,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayInsert(selectClause.asExpression(), position, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
