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
) : ArrayFunctionExpression<T>(array, listOf(value, *additionalValues.toTypedArray()))
fun <T : ValidType> TypeExpression<ArrayType<T>>.put(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayPutExpression(this, value, additionalValues.toList())

fun TypeExpression<ArrayType<StringType>>.put(
    value: String,
    vararg additionalValues: String,
) = put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<NumberType>>.put(
    value: Number,
    vararg additionalValues: Number,
) = put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<ArrayType<BooleanType>>.put(
    value: Boolean,
    vararg additionalValues: Boolean,
) = put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun <T : ValidType> ISelectOffsetClause<T>.put(
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = asExpression().put(value, *additionalValues)

fun ISelectOffsetClause<StringType>.put(
    value: String,
    vararg additionalValues: String,
) = asExpression().put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<NumberType>.put(
    value: Number,
    vararg additionalValues: Number,
) = asExpression().put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun ISelectOffsetClause<BooleanType>.put(
    value: Boolean,
    vararg additionalValues: Boolean,
) = asExpression().put(value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
