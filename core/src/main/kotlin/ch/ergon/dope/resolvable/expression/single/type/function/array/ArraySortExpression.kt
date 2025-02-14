package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArraySortExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_SORT", array)

fun <T : ValidType> arraySort(array: TypeExpression<ArrayType<T>>) = ArraySortExpression(array)

fun <T : ValidType> arraySort(selectClause: ISelectOffsetClause<T>) = arraySort(selectClause.asExpression())
