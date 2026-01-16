package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayPrependExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : FunctionOperator<ArrayType<T>>
fun <T : ValidType> TypeExpression<ArrayType<T>>.prepend(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayPrependExpression(this, value, additionalValues.toList())

fun TypeExpression<ArrayType<StringType>>.prepend(
    value: String,
    vararg additionalValues: String,
) = prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.prepend(
    value: Number,
    vararg additionalValues: Number,
) = prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.prepend(
    value: Boolean,
    vararg additionalValues: Boolean,
) = prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.prepend(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().prepend(value, *additionalValues)

fun ISelectOffsetClause<StringType>.prepend(
    value: String,
    vararg additionalValues: String,
) = asExpression().prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.prepend(
    value: Number,
    vararg additionalValues: Number,
) = asExpression().prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.prepend(
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().prepend(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
