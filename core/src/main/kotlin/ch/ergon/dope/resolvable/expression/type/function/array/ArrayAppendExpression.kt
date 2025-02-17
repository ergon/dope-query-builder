package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayAppendExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_APPEND", array, value, *additionalValues)

fun <T : ValidType> arrayAppend(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayAppendExpression(array, value, *additionalValues)

fun arrayAppend(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayAppend(
    selectClause: ISelectOffsetClause<T>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayAppend(selectClause.asExpression(), value, *additionalValues)

fun arrayAppend(
    selectClause: ISelectOffsetClause<StringType>,
    value: String,
    vararg additionalValues: String,
) = arrayAppend(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    selectClause: ISelectOffsetClause<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = arrayAppend(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    selectClause: ISelectOffsetClause<BooleanType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayAppend(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
