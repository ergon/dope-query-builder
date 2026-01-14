package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayCountExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
) : FunctionOperator<NumberType>

fun <T : ValidType> arrayCount(array: TypeExpression<ArrayType<T>>) = ArrayCountExpression(array)

fun <T : ValidType> arrayCount(selectClause: ISelectOffsetClause<T>) = arrayCount(selectClause.asExpression())
