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

data class ArrayBinarySearchExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
) : FunctionExpression<NumberType>(listOf(array, value))

fun <T : ValidType> TypeExpression<ArrayType<T>>.binarySearch(
    value: TypeExpression<T>,
) = ArrayBinarySearchExpression(this, value)

fun TypeExpression<ArrayType<StringType>>.binarySearch(
    value: String,
) = binarySearch(value.toDopeType())

fun TypeExpression<ArrayType<NumberType>>.binarySearch(
    value: Number,
) = binarySearch(value.toDopeType())

fun TypeExpression<ArrayType<BooleanType>>.binarySearch(
    value: Boolean,
) = binarySearch(value.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.binarySearch(
    value: TypeExpression<T>,
) = asExpression().binarySearch(value)

fun ISelectOffsetClause<StringType>.binarySearch(
    value: String,
) = asExpression().binarySearch(value.toDopeType())

fun ISelectOffsetClause<NumberType>.binarySearch(
    value: Number,
) = asExpression().binarySearch(value.toDopeType())

fun ISelectOffsetClause<BooleanType>.binarySearch(
    value: Boolean,
) = asExpression().binarySearch(value.toDopeType())
