package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayMaxExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>(listOf(array))

fun <T : ValidType> TypeExpression<ArrayType<T>>.max() = ArrayMaxExpression(this)

fun <T : ValidType> ISelectOffsetClause<T>.max() = asExpression().max()
