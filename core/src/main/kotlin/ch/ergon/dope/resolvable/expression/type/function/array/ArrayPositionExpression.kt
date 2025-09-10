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
) : TypeExpression<NumberType>, FunctionOperator

fun <T : ValidType> arrayPosition(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayPositionExpression(array, value)

fun <T : ValidType> arrayPosition(selectClause: ISelectOffsetClause<T>, value: TypeExpression<T>) =
    arrayPosition(selectClause.asExpression(), value)
