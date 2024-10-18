package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayMoveExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    from: TypeExpression<NumberType>,
    to: TypeExpression<NumberType>,
) : ArrayFunctionExpression<T>("ARRAY_MOVE", array, from, to)

fun <T : ValidType> arrayMove(array: TypeExpression<ArrayType<T>>, from: TypeExpression<NumberType>, to: TypeExpression<NumberType>) =
    ArrayMoveExpression(array, from, to)

fun <T : ValidType> arrayMove(array: TypeExpression<ArrayType<T>>, from: TypeExpression<NumberType>, to: Number) =
    arrayMove(array, from, to.toDopeType())

fun <T : ValidType> arrayMove(array: TypeExpression<ArrayType<T>>, from: Number, to: TypeExpression<NumberType>) =
    arrayMove(array, from.toDopeType(), to)

fun <T : ValidType> arrayMove(array: TypeExpression<ArrayType<T>>, from: Number, to: Number) =
    arrayMove(array, from.toDopeType(), to.toDopeType())

fun <T : ValidType> arrayMove(selectClause: ISelectOffsetClause<T>, from: TypeExpression<NumberType>, to: TypeExpression<NumberType>) =
    arrayMove(selectClause.asExpression(), from, to)

fun <T : ValidType> arrayMove(selectClause: ISelectOffsetClause<T>, from: TypeExpression<NumberType>, to: Number) =
    arrayMove(selectClause.asExpression(), from, to.toDopeType())

fun <T : ValidType> arrayMove(selectClause: ISelectOffsetClause<T>, from: Number, to: TypeExpression<NumberType>) =
    arrayMove(selectClause.asExpression(), from.toDopeType(), to)

fun <T : ValidType> arrayMove(selectClause: ISelectOffsetClause<T>, from: Number, to: Number) =
    arrayMove(selectClause.asExpression(), from.toDopeType(), to.toDopeType())
