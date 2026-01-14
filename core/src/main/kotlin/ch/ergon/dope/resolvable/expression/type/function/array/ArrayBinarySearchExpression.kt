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

data class ArrayBinarySearchExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
) : FunctionOperator<NumberType>

fun <T : ValidType> arrayBinarySearch(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
) = ArrayBinarySearchExpression(array, value)

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
) = arrayBinarySearch(array, value.toDopeType())

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
) = arrayBinarySearch(array, value.toDopeType())

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
) = arrayBinarySearch(array, value.toDopeType())

fun <T : ValidType> arrayBinarySearch(
    selectClause: ISelectOffsetClause<T>,
    value: TypeExpression<T>,
) = arrayBinarySearch(selectClause.asExpression(), value)

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<StringType>,
    value: String,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<NumberType>,
    value: Number,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<BooleanType>,
    value: Boolean,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())
