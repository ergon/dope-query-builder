package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayReverseExpression<T : ValidType>(override val array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>(array)

fun <T : ValidType> arrayReverse(array: TypeExpression<ArrayType<T>>) = ArrayReverseExpression(array)

fun <T : ValidType> arrayReverse(selectClause: ISelectOffsetClause<T>) = arrayReverse(selectClause.asExpression())
