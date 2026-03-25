package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayMoveExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val from: TypeExpression<NumberType>,
    val to: TypeExpression<NumberType>,
) : FunctionExpression<ArrayType<T>>(listOf(array, from, to))

fun <T : ValidType> TypeExpression<ArrayType<T>>.move(from: TypeExpression<NumberType>, to: TypeExpression<NumberType>) =
    ArrayMoveExpression(this, from, to)

fun <T : ValidType> TypeExpression<ArrayType<T>>.move(from: TypeExpression<NumberType>, to: Number) =
    move(from, to.toDopeType())

fun <T : ValidType> TypeExpression<ArrayType<T>>.move(from: Number, to: TypeExpression<NumberType>) =
    move(from.toDopeType(), to)

fun <T : ValidType> TypeExpression<ArrayType<T>>.move(from: Number, to: Number) =
    move(from.toDopeType(), to.toDopeType())

fun <T : ValidType> ISelectOffsetClause<T>.move(from: TypeExpression<NumberType>, to: TypeExpression<NumberType>) =
    asExpression().move(from, to)

fun <T : ValidType> ISelectOffsetClause<T>.move(from: TypeExpression<NumberType>, to: Number) =
    asExpression().move(from, to)

fun <T : ValidType> ISelectOffsetClause<T>.move(from: Number, to: TypeExpression<NumberType>) =
    asExpression().move(from, to)

fun <T : ValidType> ISelectOffsetClause<T>.move(from: Number, to: Number) =
    asExpression().move(from, to)
