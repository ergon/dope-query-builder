package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayLengthExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionNumberExpression<T>("ARRAY_LENGTH", array)

fun <T : ValidType> arrayLength(array: TypeExpression<ArrayType<T>>) = ArrayLengthExpression(array)
