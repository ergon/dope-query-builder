package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

data class ArrayMinExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
) : TypeExpression<T>, FunctionOperator

fun <T : ValidType> arrayMin(array: TypeExpression<ArrayType<T>>) = ArrayMinExpression(array)

fun <T : ValidType> arrayMin(selectClause: ISelectOffsetClause<T>) = arrayMin(selectClause.asExpression())
