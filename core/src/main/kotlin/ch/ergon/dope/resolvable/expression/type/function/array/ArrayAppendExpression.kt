package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayAppendExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(array, value) + additionalValues)

fun <T : ValidType> TypeExpression<ArrayType<T>>.append(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayAppendExpression(this, value, additionalValues.toList())

fun TypeExpression<ArrayType<StringType>>.append(
    value: String,
    vararg additionalValues: String,
) = append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.append(
    value: Number,
    vararg additionalValues: Number,
) = append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.append(
    value: Boolean,
    vararg additionalValues: Boolean,
) = append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.append(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().append(value, *additionalValues)

fun ISelectOffsetClause<StringType>.append(
    value: String,
    vararg additionalValues: String,
) = asExpression().append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.append(
    value: Number,
    vararg additionalValues: Number,
) = asExpression().append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.append(
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().append(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
