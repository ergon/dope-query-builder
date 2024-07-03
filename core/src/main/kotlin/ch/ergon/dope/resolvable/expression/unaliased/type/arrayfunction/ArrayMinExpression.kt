package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayMinExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) : ArrayFunctionNumberExpression<T>("ARRAY_MIN", array)

fun <T : ValidType> arrayMin(array: TypeExpression<ArrayType<T>>) = ArrayMinExpression(array)
