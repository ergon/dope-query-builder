package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArraySortExpression<T : ValidType>(override val array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>(array)

fun <T : ValidType> arraySort(array: TypeExpression<ArrayType<T>>) = ArraySortExpression(array)

fun <T : ValidType> arraySort(selectClause: ISelectOffsetClause<T>) = arraySort(selectClause.asExpression())
