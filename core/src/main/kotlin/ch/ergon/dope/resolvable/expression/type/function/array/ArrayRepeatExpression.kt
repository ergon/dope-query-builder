package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayRepeatExpression<T : ValidType>(
    val value: TypeExpression<T>,
    val repetitions: TypeExpression<NumberType>,
) : ArrayFunctionExpression<ArrayType<T>>(listOf(value, repetitions))

fun <T : ValidType> arrayRepeat(
    value: TypeExpression<T>,
    repetitions: TypeExpression<NumberType>,
) = ArrayRepeatExpression(value, repetitions)

fun <T : ValidType> arrayRepeat(
    value: TypeExpression<T>,
    repetitions: Number,
) = arrayRepeat(value, repetitions.toDopeType())
