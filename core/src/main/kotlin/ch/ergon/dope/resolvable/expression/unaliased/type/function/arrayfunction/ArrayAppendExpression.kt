package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayAppendExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_APPEND", array, value, *additionalValues)

fun <T : ValidType> arrayAppend(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayAppendExpression(array, value, *additionalValues)

fun arrayAppend(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayAppend(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayAppend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
