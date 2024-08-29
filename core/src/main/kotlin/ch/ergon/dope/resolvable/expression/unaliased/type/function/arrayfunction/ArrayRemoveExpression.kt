package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayRemoveExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_REMOVE", array, value, *additionalValues)

fun <T : ValidType> arrayRemove(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayRemoveExpression(array, value, *additionalValues)

fun arrayRemove(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayRemove(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayRemove(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
