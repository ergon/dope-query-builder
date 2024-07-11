package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayExceptExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_EXCEPT", array, except)

fun <T : ValidType> arrayExcept(array: TypeExpression<ArrayType<T>>, except: TypeExpression<ArrayType<T>>) =
    ArrayExceptExpression(array, except)
