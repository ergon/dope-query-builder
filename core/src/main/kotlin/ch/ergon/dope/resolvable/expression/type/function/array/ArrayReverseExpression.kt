package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayReverseExpression<T : ValidType>(val array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<ArrayType<T>>(listOf(array))

fun <T : ValidType> TypeExpression<ArrayType<T>>.reverse() = ArrayReverseExpression(this)

fun <T : ValidType> ISelectOffsetClause<T>.reverse() = asExpression().reverse()
