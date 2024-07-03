package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayIfNullExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) : ArrayFunctionNumberExpression<T>("ARRAY_IFNULL", array)

fun <T : ValidType> arrayIfNull(array: TypeExpression<ArrayType<T>>) = ArrayIfNullExpression(array)
