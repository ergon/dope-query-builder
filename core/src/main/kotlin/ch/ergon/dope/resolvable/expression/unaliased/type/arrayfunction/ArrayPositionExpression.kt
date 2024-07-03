package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayPositionExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, value: TypeExpression<NumberType>) :
    ArrayFunctionNumberExpression<T>("ARRAY_POSITION", array, value)

fun <T : ValidType> arrayPosition(array: TypeExpression<ArrayType<T>>, value: TypeExpression<NumberType>) = ArrayPositionExpression(array, value)

fun <T : ValidType> arrayPosition(array: TypeExpression<ArrayType<T>>, value: Number) = arrayPosition(array, value.toDopeType())
