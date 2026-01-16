package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

data class ArraySumExpression<T : NumberType>(
    val array: TypeExpression<ArrayType<T>>,
) : FunctionOperator<NumberType>

fun <T : NumberType> TypeExpression<ArrayType<T>>.sum() = ArraySumExpression(this)

fun <T : NumberType> ISelectOffsetClause<T>.sum() = asExpression().sum()
