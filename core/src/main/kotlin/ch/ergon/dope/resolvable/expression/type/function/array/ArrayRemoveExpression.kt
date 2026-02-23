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

data class ArrayRemoveExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
    val additionalValues: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<ArrayType<T>>(listOf(array, value) + additionalValues)

fun <T : ValidType> TypeExpression<ArrayType<T>>.remove(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayRemoveExpression(this, value, additionalValues.toList())

fun TypeExpression<ArrayType<StringType>>.remove(
    value: String,
    vararg additionalValues: String,
) = remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.remove(
    value: Number,
    vararg additionalValues: Number,
) = remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.remove(
    value: Boolean,
    vararg additionalValues: Boolean,
) = remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.remove(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().remove(value, *additionalValues)

fun ISelectOffsetClause<StringType>.remove(
    value: String,
    vararg additionalValues: String,
) = asExpression().remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.remove(
    value: Number,
    vararg additionalValues: Number,
) = asExpression().remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.remove(
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().remove(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
