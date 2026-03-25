package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArraySortExpression<T : ValidType>(val array: TypeExpression<ArrayType<T>>) : FunctionExpression<ArrayType<T>>(listOf(array))

fun <T : ValidType> TypeExpression<ArrayType<T>>.sort() = ArraySortExpression(this)

fun <T : ValidType> ISelectOffsetClause<T>.sort() = asExpression().sort()
