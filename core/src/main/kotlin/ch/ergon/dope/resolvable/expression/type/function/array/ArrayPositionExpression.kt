package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class ArrayPositionExpression<T : ValidType>(
    val array: TypeExpression<ArrayType<T>>,
    val value: TypeExpression<T>,
) : FunctionOperator<NumberType>

fun <T : ValidType> TypeExpression<ArrayType<T>>.position(value: TypeExpression<T>) =
    ArrayPositionExpression(this, value)

fun <T : ValidType> ISelectOffsetClause<T>.position(value: TypeExpression<T>) =
    asExpression().position(value)
