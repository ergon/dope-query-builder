package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayDistinctExpression<T : ValidType>(array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>("ARRAY_DISTINCT", array)

fun <T : ValidType> arrayDistinct(array: TypeExpression<ArrayType<T>>) = ArrayDistinctExpression(array)

fun <T : ValidType> arrayDistinct(selectClause: ISelectOffsetClause<T>) = arrayDistinct(selectClause.asExpression())
