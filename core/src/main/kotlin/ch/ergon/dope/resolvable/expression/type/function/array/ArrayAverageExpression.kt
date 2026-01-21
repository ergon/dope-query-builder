package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

data class ArrayAverageExpression<T : NumberType>(
    val array: TypeExpression<ArrayType<T>>,
) : ArrayFunctionExpression<T>(listOf(array))

fun <T : NumberType> TypeExpression<ArrayType<T>>.average() = ArrayAverageExpression(this)

fun <T : NumberType> ISelectOffsetClause<T>.average() = asExpression().average()
