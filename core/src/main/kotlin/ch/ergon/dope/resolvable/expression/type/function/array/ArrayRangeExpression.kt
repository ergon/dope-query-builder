package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

data class ArrayRangeExpression(
    val start: TypeExpression<NumberType>,
    val end: TypeExpression<NumberType>,
    val step: TypeExpression<NumberType>? = null,
) : ArrayFunctionExpression<ArrayType<NumberType>>(listOfNotNull(start, end, step))

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: TypeExpression<NumberType>,
    step: TypeExpression<NumberType>,
) = ArrayRangeExpression(start, end, step)

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: TypeExpression<NumberType>,
    step: Number? = null,
) = ArrayRangeExpression(start, end, step?.toDopeType())

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: Number,
    step: TypeExpression<NumberType>,
) = arrayRange(start, end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: TypeExpression<NumberType>,
    step: TypeExpression<NumberType>,
) = arrayRange(start.toDopeType(), end, step)

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: Number,
    step: Number? = null,
) = arrayRange(start, end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: TypeExpression<NumberType>,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end, step)

fun arrayRange(
    start: Number,
    end: Number,
    step: TypeExpression<NumberType>,
) = arrayRange(start.toDopeType(), end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: Number,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end.toDopeType(), step)
