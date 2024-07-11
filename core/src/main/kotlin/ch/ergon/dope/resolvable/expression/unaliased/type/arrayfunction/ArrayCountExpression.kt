package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayCountExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionNumberExpression<T>("ARRAY_COUNT", array)

fun <T : ValidType> arrayCount(array: TypeExpression<ArrayType<T>>) = ArrayCountExpression(array)
