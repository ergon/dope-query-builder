package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayPutExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_PUT", array, value, *additionalValues)

fun <T : ValidType> arrayPut(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayPutExpression(array, value, *additionalValues)

fun arrayPut(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPut(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayPut(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
