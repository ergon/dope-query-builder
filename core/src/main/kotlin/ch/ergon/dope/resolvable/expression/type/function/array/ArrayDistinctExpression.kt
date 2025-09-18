package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayDistinctExpression<T : ValidType>(override val array: TypeExpression<ArrayType<T>>) :
    ArrayFunctionExpression<T>(array)

fun <T : ValidType> arrayDistinct(array: TypeExpression<ArrayType<T>>) = ArrayDistinctExpression(array)

fun <T : ValidType> arrayDistinct(selectClause: ISelectOffsetClause<T>) = arrayDistinct(selectClause.asExpression())
