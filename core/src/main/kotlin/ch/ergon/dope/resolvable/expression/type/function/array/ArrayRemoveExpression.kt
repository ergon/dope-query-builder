package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayRemoveExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_REMOVE", array, value, *additionalValues)

fun <T : ValidType> arrayRemove(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayRemoveExpression(array, value, *additionalValues)

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
