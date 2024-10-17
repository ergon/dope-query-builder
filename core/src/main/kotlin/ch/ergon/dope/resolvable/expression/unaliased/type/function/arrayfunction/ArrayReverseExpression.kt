package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayReverseExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_REVERSE", array)

fun <T : ValidType> arrayReverse(array: TypeExpression<ArrayType<T>>) = ArrayReverseExpression(array)

fun <T : ValidType> arrayReverse(array: ISelectOffsetClause<T>) = arrayReverse(array.asExpression())
