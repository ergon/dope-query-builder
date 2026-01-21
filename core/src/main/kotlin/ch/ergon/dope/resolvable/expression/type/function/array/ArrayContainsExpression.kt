package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ArrayContainsExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
) : ArrayFunctionExpression<BooleanType>(listOf(array, value))

fun <T : ValidType> TypeExpression<ArrayType<T>>.contains(value: TypeExpression<T>) =
    ArrayContainsExpression(this, value)

fun TypeExpression<ArrayType<StringType>>.contains(value: String) =
    contains(value.toDopeType())

fun TypeExpression<ArrayType<NumberType>>.contains(value: Number) =
    contains(value.toDopeType())

fun TypeExpression<ArrayType<BooleanType>>.contains(value: Boolean) =
    contains(value.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.contains(value: TypeExpression<T>) =
    asExpression().contains(value)

fun ISelectOffsetClause<StringType>.contains(value: String) =
    asExpression().contains(value.toDopeType())

fun ISelectOffsetClause<NumberType>.contains(value: Number) =
    asExpression().contains(value.toDopeType())

fun ISelectOffsetClause<BooleanType>.contains(value: Boolean) =
    asExpression().contains(value.toDopeType())
