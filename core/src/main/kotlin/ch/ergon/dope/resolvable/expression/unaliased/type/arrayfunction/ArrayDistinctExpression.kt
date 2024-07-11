package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayDistinctExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_DISTINCT", array)

fun <T : ValidType> arrayDistinct(array: TypeExpression<ArrayType<T>>) = ArrayDistinctExpression(array)
