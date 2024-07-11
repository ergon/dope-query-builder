package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayInsertExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) : ArrayFunctionExpression<T>("ARRAY_INSERT", array, position, value, *additionalValues)

fun <T : ValidType> arrayInsert(
    array: TypeExpression<ArrayType<T>>,
    position: TypeExpression<NumberType>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayInsertExpression(array, position, value, *additionalValues)

fun <T : ValidType> arrayInsert(
    array: TypeExpression<ArrayType<T>>,
    position: Number,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = arrayInsert(array, position.toDopeType(), value, *additionalValues)
