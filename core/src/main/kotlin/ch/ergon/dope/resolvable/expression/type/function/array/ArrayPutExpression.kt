package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayPutExpression<T : ValidType>(
    override val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : ArrayFunctionExpression<T>("ARRAY_PUT", array, listOf(value, *additionalValues.toTypedArray()))

fun <T : ValidType> arrayPut(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayPutExpression(array, value, additionalValues.toList())

fun arrayPut(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> arrayPut(
    selectClause: ISelectOffsetClause<T>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayPut(selectClause.asExpression(), value, *additionalValues)

fun arrayPut(
    selectClause: ISelectOffsetClause<StringType>,
    value: String,
    vararg additionalValues: String,
) = arrayPut(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    selectClause: ISelectOffsetClause<NumberType>,
    value: Number,
    vararg additionalValues: Number,
) = arrayPut(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    selectClause: ISelectOffsetClause<BooleanType>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayPut(selectClause.asExpression(), value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
