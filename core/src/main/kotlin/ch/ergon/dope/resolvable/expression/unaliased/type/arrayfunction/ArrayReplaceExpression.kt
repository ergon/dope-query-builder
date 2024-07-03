package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayReplaceExpression<T : ValidType>(
    array: TypeExpression<ArrayType<T>>,
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) : ArrayFunctionExpression<T>("ARRAY_REPLACE", array, toReplace, replaceWith, extra = max)

fun <T : ValidType> arrayReplace(
    array: TypeExpression<ArrayType<T>>,
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: TypeExpression<NumberType>? = null,
) = ArrayReplaceExpression(array, toReplace, replaceWith, max)

fun <T : ValidType> arrayReplace(
    array: TypeExpression<ArrayType<T>>,
    toReplace: TypeExpression<T>,
    replaceWith: TypeExpression<T>,
    max: Number? = null,
) = arrayReplace(array, toReplace, replaceWith, max?.toDopeType())
